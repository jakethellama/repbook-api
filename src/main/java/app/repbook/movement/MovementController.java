package app.repbook.movement;

import app.repbook.UnauthorizedException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MovementController {
    private final MovementServiceImpl movementService;
    private final SsetServiceImpl ssetService;
    private final MovTypeServiceImpl movTypeService;

    @Autowired
    public MovementController(MovementServiceImpl movementService, SsetServiceImpl ssetService, MovTypeServiceImpl movTypeService) {
        this.movementService = movementService;
        this.ssetService = ssetService;
        this.movTypeService = movTypeService;
    }

    public record postMovementRequest(String movType, Integer workout_id) {}
    public record stringResponse(String message) {}

    @PostMapping("/movements")
    public MovementDTO postMovement(HttpSession session, @RequestBody postMovementRequest req) {
        Integer user_id = (Integer) session.getAttribute("user_id");

        if (user_id == null) {
            throw new UnauthorizedException();
        }

        MovementDTO newMovement = movementService.createMovement(req.movType(), req.workout_id(), user_id);
        return newMovement;
    }

    public record patchMovementRequest(String movType, String notes, Integer restAfter) {}

    @PatchMapping("/movements/{movement_id}")
    public MovementDTO patchMovementInfo(HttpSession session, @PathVariable Integer movement_id, @RequestBody patchMovementRequest req) {
        Integer user_id = (Integer) session.getAttribute("user_id");

        if (user_id == null) {
            throw new UnauthorizedException();
        }

        MovementDTO upMovement = movementService.patchMovementInfoById(movement_id, req.movType(), req.notes(), req.restAfter(), user_id);
        return upMovement;
    }

    @DeleteMapping("/movements/{movement_id}")
    public stringResponse deleteMovement(HttpSession session, @PathVariable Integer movement_id) {
        Integer user_id = (Integer) session.getAttribute("user_id");

        if (user_id == null) {
            throw new UnauthorizedException();
        }

        movementService.deleteMovementById(movement_id, user_id);
        return new stringResponse("Deleted movement");
    }
}
