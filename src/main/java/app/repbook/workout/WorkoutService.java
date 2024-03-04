package app.repbook.workout;

import java.time.LocalDate;
import java.util.List;

public interface WorkoutService {
    WorkoutDTO createWorkout(String name, LocalDate date, Integer user_id);
    WorkoutDTO getWorkoutById(Integer workout_id, Integer user_id);
    List<WorkoutDTO> getWorkoutsInfoByUsername(String username, Integer user_id);
    WorkoutDTO patchWorkoutInfoById(Integer workout_id, String name, LocalDate date, String notes, Integer user_id);
    void deleteWorkoutById(Integer workout_id, Integer user_id);
}
