package br.com.customers.service.mapper;

import br.com.customers.domain.Client;
import br.com.customers.service.dto.ClientDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = {AddressMapper.class})
public interface ClientMapper extends EntityMapper<ClientDTO, Client> {

    @Mapping(source = "client.addresses", target = "addresses")
    ClientDTO toDto(Client client);

    @Mapping(target = "addresses", ignore = true)
    Client toEntity(ClientDTO clientDTO);

    default Client fromId(Long id) {
        if (id == null) {
            return null;
        }
        Client client = new Client();
        client.setId(id);
        return client;
    }
}
