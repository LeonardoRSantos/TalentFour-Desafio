package br.com.talentfour.domain.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.talentfour.domain.entity.UserProfile;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long>, JpaSpecificationExecutor<UserProfile> {
}
