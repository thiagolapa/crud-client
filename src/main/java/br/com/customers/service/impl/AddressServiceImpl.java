package br.com.customers.service.impl;

import br.com.customers.domain.Address;
import br.com.customers.repository.AddressRepository;
import br.com.customers.service.AddressService;
import br.com.customers.service.dto.AddressDTO;
import br.com.customers.service.mapper.AddressMapper;
import br.com.customers.service.util.StringUtils;
import br.com.customers.service.util.ThrowUtils;
import br.com.customers.web.rest.errors.BadRequestAlertException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class AddressServiceImpl implements AddressService {

    private final Logger log = LoggerFactory.getLogger(AddressServiceImpl.class);

    private static final String ENTITY_NAME = "address";

    private final AddressRepository addressRepository;

    private final AddressMapper addressMapper;

    public AddressServiceImpl(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    /**
     * Save a address.
     *
     * @param addressDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AddressDTO save(AddressDTO addressDTO) {
        log.debug("Request to save Address : {}", addressDTO);
        validate(addressDTO);
        Address address = addressRepository.save(addressMapper.toEntity(addressDTO));
        return addressMapper.toDto(address);
    }

    /**
     *  validate address.
     * @param addressDTO
     */
    public void validate(AddressDTO addressDTO) {
        if(addressDTO.getId() == null) {
            validateCreation(addressDTO);
        } else {
            validateUpdate(addressDTO);
        }
    }

    /**
     *
     * @param parameter
     * @return
     */
    public String isNullOrEmpty(String parameter) {
        return StringUtils.isNullOrEmpty(parameter) ? null : parameter.toUpperCase();
    }

    /**
     * Get all the addresses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AddressDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Addresses");
        return addressRepository.findAll(pageable)
            .map(addressMapper::toDto);
    }

    /**
     * Get one address by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AddressDTO> findOne(Long id) {
        log.debug("Request to get Address : {}", id);
        Optional<Address> address = addressRepository.findById(id);
        return address.map(addressMapper::toDto);
    }

    /**
     * Delete the address by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Address : {}", id);
        Optional<Address> address = addressRepository.findById(id);
        validatePresence(address);
        addressRepository.deleteById(id);
    }

    @Override
    public void validateCreation(AddressDTO addressDTO) {
        if (addressDTO.getId() != null) {
            ThrowUtils.badRequest("Address cannot have an ID", ENTITY_NAME, "idexists");
        }
        validateFields(addressDTO);
    }

    @Override
    public void validateUpdate(AddressDTO addressDTO) {
        if (addressDTO.getId() == null) {
            ThrowUtils.badRequest("Address cannot have ID null", ENTITY_NAME, "idnull");
        }
        validateFields(addressDTO);
        Optional<Address> address = addressRepository.findById(addressDTO.getId());
    }

    @Override
    public List<AddressDTO> findAddressesByClientId(Long personId) {
        return addressMapper.toDto(addressRepository.findAddressesByClientId(personId));
    }

    @Override
    public void removeAll(List<Address> addresses) {
        addressRepository.deleteAll(addresses);
    }

    /**
     *
     * @param addressDTO
     */
    private void validateFields(AddressDTO addressDTO) {
        if (addressDTO.getStreetAddress().isEmpty()) {
            ThrowUtils.badRequest("Address must is null", ENTITY_NAME, "addressNameNotNull");
        }
    }

    /**
     *
     * @param address
     */
    private void validatePresence(Optional<Address> address) {
        if (!address.isPresent()) {
            ThrowUtils.badRequest("Address not found", ENTITY_NAME, "addressNotFound");
        }
    }
}
