package br.com.talentfour.domain.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.talentfour.domain.entity.UserSystem;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserSystemRepository extends JpaRepository<UserSystem, Long>, JpaSpecificationExecutor<UserSystem> {
    Optional<UserSystem> findByUsername(String login);
}
