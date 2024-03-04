package app.repbook.movement;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovTypeRepository extends JpaRepository<MovType, Integer> {
    List<MovType> findAllByPerson_UsernameIsNullOrPerson_UsernameOrderByPerson_UsernameAsc(String username);
}
