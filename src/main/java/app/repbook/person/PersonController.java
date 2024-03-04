package app.repbook.person;

import app.repbook.ForbiddenException;
import app.repbook.UnauthorizedException;
import app.repbook.movement.MovTypeDTO;
import app.repbook.movement.MovTypeService;
import app.repbook.workout.WorkoutDTO;
import app.repbook.workout.WorkoutServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PersonController {
    private final PersonServiceImpl personService;
    private final WorkoutServiceImpl workoutService;
    private final MovTypeService movTypeService;

    @Autowired
    public PersonController(PersonServiceImpl personService, WorkoutServiceImpl workoutService, MovTypeService movTypeService) {
        this.personService = personService;
        this.workoutService = workoutService;
        this.movTypeService = movTypeService;
    }

    public record userPassRequest(String username, String password) {}
    public record stringResponse(String message) {}

    @PostMapping("/users")
    public stringResponse postPerson(HttpSession session, @RequestBody userPassRequest req) {
        Integer user_id = (Integer) session.getAttribute("user_id");

        if (user_id != null) {
            throw new ForbiddenException();
        }

        PersonDTO newPerson = personService.createPerson(req.username(), req.password());
        session.setAttribute("user_id", newPerson.getId());
        session.setAttribute("username", newPerson.getUsername());
        return new stringResponse("User created");
    }

    @GetMapping("/users/{username}/workouts")
    public List<WorkoutDTO> getPersonWorkoutsInfo(HttpSession session, @PathVariable String username) {
        Integer user_id = (Integer) session.getAttribute("user_id");

        if (user_id == null) {
            throw new UnauthorizedException();
        }

        List<WorkoutDTO> workouts = workoutService.getWorkoutsInfoByUsername(username, user_id);
        return workouts;
    }

    @GetMapping("/users/{username}/movtypes")
    public List<MovTypeDTO> getPersonMovTypes(HttpSession session, @PathVariable String username) {
        Integer user_id = (Integer) session.getAttribute("user_id");

        if (user_id == null) {
            throw new UnauthorizedException();
        }

        List<MovTypeDTO> movTypes = movTypeService.getMovTypesByUsername(username, user_id);
        return movTypes;
    }


}
