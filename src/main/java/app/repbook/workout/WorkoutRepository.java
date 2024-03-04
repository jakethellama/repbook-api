package app.repbook.workout;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkoutRepository extends JpaRepository<Workout, Integer> {
    List<Workout> findAllByPerson_UsernameOrderByDateDescIdDesc(String username);
}
