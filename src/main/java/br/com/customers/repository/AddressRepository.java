package br.com.customers.repository;
import br.com.customers.domain.Address;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Address entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    /**
     *
     * @param clientId
     * @return
     */
    @Query("SELECT a "
        + "FROM Address a "
        + "WHERE a.client.id = ?1")
    List<Address> findAddressesByClientId(Long clientId);

}
