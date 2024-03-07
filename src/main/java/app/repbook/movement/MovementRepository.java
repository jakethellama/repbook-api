package app.repbook.movement;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovementRepository extends JpaRepository<Movement, Integer> {
    List<Movement> findAllByWorkout_Id(Integer id);

}
