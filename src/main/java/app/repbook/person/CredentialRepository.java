package app.repbook.person;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CredentialRepository extends JpaRepository<Credential, Integer> {
    Optional<Credential> findByUsername(String username);
}
