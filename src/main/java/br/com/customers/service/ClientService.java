package br.com.customers.service;

import br.com.customers.service.dto.ClientDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

/**
 * Interface for access to business rules.
 */
public interface ClientService {

    /**
     * Save a client.
     *
     * @param clientDTO the entity to save.
     * @return the persisted entity.
     */
    ClientDTO save(ClientDTO clientDTO);

    /**
     * Get all the customers.
     * @param pageable
     * @return the list of entities.
     */
    Page<ClientDTO> findAll(Pageable pageable);

    /**
     * Get all the customers.
     * @param query
     * @param paginate
     * @param pageable
     * @return the list of entities.
     */
    ResponseEntity<List<ClientDTO>> findAll(String query, String risk, Boolean paginate, Pageable pageable);

    /**
     * Get the "id" client.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ClientDTO> findOne(Long id);

    /**
     * Delete the "id" client.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Method to validate customer creation.
     * @param clientDTO
     */
    void validateCreation(ClientDTO clientDTO);

    /**
     * Method to validate client editing.
     * @param clientDTO
     */
    void validateUpdate(ClientDTO clientDTO);
}
