package app.repbook.workout;

import app.repbook.UnauthorizedException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class WorkoutController {
    private final WorkoutServiceImpl workoutService;

    @Autowired
    public WorkoutController(WorkoutServiceImpl workoutService) {
        this.workoutService = workoutService;
    }

    public record stringResponse(String message) {}

    public record postWorkoutRequest(String name, LocalDate date) {}

    @PostMapping("/workouts")
    public WorkoutDTO postWorkout(HttpSession session, @RequestBody postWorkoutRequest req) {
        Integer user_id = (Integer) session.getAttribute("user_id");

        if (user_id == null) {
            throw new UnauthorizedException();
        }

        WorkoutDTO newWorkout = workoutService.createWorkout(req.name(), req.date(), user_id);
        return newWorkout;
    }

    @GetMapping("/workouts/{workout_id}")
    public WorkoutDTO getWorkout(HttpSession session, @PathVariable Integer workout_id) {
        Integer user_id = (Integer) session.getAttribute("user_id");

        if (user_id == null) {
            throw new UnauthorizedException();
        }

        WorkoutDTO workout = workoutService.getWorkoutById(workout_id, user_id);
        return workout;
    }

    public record patchWorkoutRequest(String name, LocalDate date, String notes) {}

    @PatchMapping("/workouts/{workout_id}")
    public WorkoutDTO patchWorkoutInfo(HttpSession session, @PathVariable Integer workout_id, @RequestBody patchWorkoutRequest req) {
        Integer user_id = (Integer) session.getAttribute("user_id");

        if (user_id == null) {
            throw new UnauthorizedException();
        }

        WorkoutDTO upWorkout = workoutService.patchWorkoutInfoById(workout_id, req.name(), req.date(), req.notes(), user_id);
        return upWorkout;
    }

    @DeleteMapping("/workouts/{workout_id}")
    public stringResponse deleteWorkout(HttpSession session, @PathVariable Integer workout_id) {
        Integer user_id = (Integer) session.getAttribute("user_id");

        if (user_id == null) {
            throw new UnauthorizedException();
        }

        workoutService.deleteWorkoutById(workout_id, user_id);
        return new stringResponse("Deleted workout");
    }
}
