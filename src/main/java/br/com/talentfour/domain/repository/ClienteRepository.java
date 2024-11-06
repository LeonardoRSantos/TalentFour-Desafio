package br.com.talentfour.domain.repository;

import br.com.talentfour.controller.dto.request.ClienteRequestToFilterDto;
import br.com.talentfour.domain.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long>, JpaSpecificationExecutor<Cliente> {

    @Query(value = "SELECT c.* FROM talentfour.cliente c " +
            "LEFT JOIN talentfour.address a ON c.address_id = a.id " +
            "WHERE (:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:email IS NULL OR LOWER(c.email) LIKE LOWER(CONCAT('%', :email, '%'))) " +
            "AND (:state IS NULL OR LOWER(a.state) LIKE LOWER(CONCAT('%', :state, '%'))) " +
            "ORDER BY c.name",
            nativeQuery = true)
    Page<Cliente> filter(@Param("name") String name,
                         @Param("email") String email,
                         @Param("state") String state,
                         Pageable pageable);



}
