package br.com.customers.repository;
import br.com.customers.domain.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;


/**
 * Spring Data  repository for the Client entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    /**
     * @param query
     * @param pageable
     * @return
     */
    @Query("SELECT client FROM Client client "
        + "WHERE (?1 IS NULL OR UPPER(client.name) LIKE %?1%) "
        + "AND (?2 IS NULL OR client.risk = ?2) ")
    Page<Client> findAll(String query, String risk, Pageable pageable);

    /**
     *
     * @param name
     * @return
     */
    @Query("SELECT CASE WHEN COUNT(client) > 0 THEN 'true' ELSE 'false' END "
        + "FROM Client client "
        + "WHERE client.name = ?1 ")
    boolean existsWithName(String name);

    /**
     *
     * @param name
     * @param clientId
     * @return
     */
    @Query("SELECT CASE WHEN COUNT(client) > 0 THEN 'true' ELSE 'false' END "
        + "FROM Client client "
        + "WHERE client.name = ?1 "
        + "AND client.id != ?2 ")
    boolean existsWithName(String name, Long clientId);

}
