package br.com.customers.service.mapper;

import br.com.customers.domain.Address;
import br.com.customers.service.dto.AddressDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = {ClientMapper.class})
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {

    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "client.name", target = "clientName")
    AddressDTO toDto(Address address);

    @Mapping(source = "clientId", target = "client")
    Address toEntity(AddressDTO addressDTO);

    default Address fromId(Long id) {
        if (id == null) {
            return null;
        }
        Address Address = new Address();
        Address.setId(id);
        return Address;
    }
}
