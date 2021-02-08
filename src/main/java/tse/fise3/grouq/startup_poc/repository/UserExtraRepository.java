package tse.fise3.grouq.startup_poc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tse.fise3.grouq.startup_poc.domain.UserExtra;

@Repository
public interface UserExtraRepository extends JpaRepository<UserExtra, Long>{

}
