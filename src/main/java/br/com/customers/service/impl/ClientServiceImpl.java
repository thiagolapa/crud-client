package br.com.customers.service.impl;

import br.com.customers.domain.Client;
import br.com.customers.repository.ClientRepository;
import br.com.customers.service.AddressService;
import br.com.customers.service.ClientService;
import br.com.customers.service.dto.AddressDTO;
import br.com.customers.service.dto.ClientDTO;
import br.com.customers.service.mapper.AddressMapper;
import br.com.customers.service.mapper.ClientMapper;
import br.com.customers.service.util.StringUtils;
import br.com.customers.service.util.ThrowUtils;
import io.github.jhipster.web.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    private final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    private static final String ENTITY_NAME = "client";

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientMapper clientMapper;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private AddressService addressService;

    /**
     * Save a client.
     *
     * @param clientDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ClientDTO save(ClientDTO clientDTO) {
        log.debug("Request to save Client : {}", clientDTO);
        validate(clientDTO);
        Client client = clientRepository.save(clientMapper.toEntity(clientDTO));
        createAddress(client, clientDTO);
        return clientMapper.toDto(client);
    }

    /**
     *
     * @param client
     * @param clientDTO
     */
    private void createAddress(Client client, ClientDTO clientDTO) {
        if(!clientDTO.getAddresses().isEmpty())
            removeAllAddress(clientDTO);
            clientDTO.getAddresses().stream().map(addressDTO -> {
                addressDTO.setClientId(client.getId());
                return addressService.save(addressDTO);
            }).collect(Collectors.toList());
    }

    /**
     * remove all address.
     */
    public void removeAllAddress(ClientDTO clientDTO) {
        if (clientDTO.getId() != null) {
            addressService.removeAll(addressMapper.toEntity(clientDTO.getAddresses()));
        }
    }

    /**
     *  validate client.
     * @param clientDTO
     */
    public void validate(ClientDTO clientDTO) {
        if (clientDTO.getAddresses().isEmpty()) {
            ThrowUtils.badRequest("Address not add.", ENTITY_NAME, "addressNotAdd");
        }
        if (clientDTO.getId() == null) {
            validateCreation(clientDTO);
        } else {
            validateUpdate(clientDTO);
        }
    }

    /**
     * Get all the customers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public ResponseEntity<List<ClientDTO>> findAll(String query, String risk, Boolean paginate, Pageable pageable) {
        log.debug("Request to get all Customers");
        query = isNullOrEmpty(query);
        risk = isNullOrEmpty(risk);
        Page<ClientDTO> clientDTOS;
        if (Boolean.TRUE.equals(paginate)) {
            clientDTOS = clientRepository.findAll(query, risk, pageable).map(clientMapper::toDto);
            HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), clientDTOS);
            return new ResponseEntity<>(clientDTOS.getContent(), headers, HttpStatus.OK);
        }
        clientDTOS = this.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), clientDTOS);
        return ResponseEntity.ok().headers(headers).body(clientDTOS.getContent());
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
     * Get all the customers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClientDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Customers");
        return clientRepository.findAll(pageable)
            .map(clientMapper::toDto);
    }

    /**
     * Get one client by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ClientDTO> findOne(Long id) {
        log.debug("Request to get Client : {}", id);
        Optional<ClientDTO> clientDTO = clientRepository.findById(id).map(clientMapper::toDto);
        return clientDTO;
    }

    /**
     * Delete the client by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Client : {}", id);
        Optional<Client> client = clientRepository.findById(id);
        if (!client.isPresent()) {
            ThrowUtils.badRequest("Client cannot have an ID", ENTITY_NAME, "idexists");
        }
        List<AddressDTO> addressDTOS = addressService.findAddressesByClientId(id);
        if (!addressDTOS.isEmpty()) {
            addressService.removeAll(addressMapper.toEntity(addressDTOS));
        }
        clientRepository.deleteById(id);
    }

    @Override
    public void validateCreation(ClientDTO clientDTO) {
        if (clientDTO.getId() != null) {
            ThrowUtils.badRequest("Client cannot have an ID", ENTITY_NAME, "idexists");
        }
        if (clientRepository.existsWithName(clientDTO.getName())) {
            ThrowUtils.badRequest("Client exists", ENTITY_NAME, "clientNameExists");
        }
        validateFields(clientDTO);
    }

    @Override
    public void validateUpdate(ClientDTO clientDTO) {
        if (clientDTO.getId() == null) {
            ThrowUtils.badRequest("Client cannot have ID null", ENTITY_NAME, "idnull");
        }
        if (clientRepository.existsWithName(clientDTO.getName(), clientDTO.getId())) {
            ThrowUtils.badRequest("Client exists", ENTITY_NAME, "clientNameExists");
        }
        validateFields(clientDTO);
    }

    /**
     *
     * @param clientDTO
     */
    private void validateFields(ClientDTO clientDTO) {
        if (clientDTO.getName() == null) {
            ThrowUtils.badRequest("Client must is null", ENTITY_NAME, "clientNameNotNull");
        }
        if (clientDTO.getMonthlyIncome() == null) {
            ThrowUtils.badRequest("Client cpf already exists", ENTITY_NAME, "clientMonthlyIncomeNotNull");
        }
        if (clientDTO.getRisk() == null) {
            ThrowUtils.badRequest("Client name already exists", ENTITY_NAME, "clientRiskNotNull");
        }
    }

}
