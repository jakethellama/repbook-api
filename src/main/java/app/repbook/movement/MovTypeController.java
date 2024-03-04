package app.repbook.movement;

import app.repbook.UnauthorizedException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class MovTypeController {
    private final MovTypeService movTypeService;

    @Autowired
    public MovTypeController(MovTypeService movTypeService) {
        this.movTypeService = movTypeService;
    }

    public record postMovTypeRequest(String name) {}
    public record stringResponse(String message) {}

    @PostMapping("/movtypes")
    public stringResponse postMovType(HttpSession session, @RequestBody postMovTypeRequest req) {
        Integer user_id = (Integer) session.getAttribute("user_id");

        if (user_id == null) {
            throw new UnauthorizedException();
        }

        Integer newMovType_id = movTypeService.createMovType(req.name(), user_id);
        return new stringResponse("Created movement type: " + newMovType_id);
    }

    public record patchMovTypeRequest(String name) {}

    @PatchMapping("/movtypes/{movType_id}")
    public stringResponse patchMovType(HttpSession session, @PathVariable Integer movType_id, @RequestBody patchMovTypeRequest req) {
        Integer user_id = (Integer) session.getAttribute("user_id");

        if (user_id == null) {
            throw new UnauthorizedException();
        }

        movTypeService.patchMovTypeById(movType_id, req.name(), user_id);
        return new stringResponse("Patched movement type");
    }

    @DeleteMapping("/movtypes/{movType_id}")
    public stringResponse deleteMovType(HttpSession session, @PathVariable Integer movType_id) {
        Integer user_id = (Integer) session.getAttribute("user_id");

        if (user_id == null) {
            throw new UnauthorizedException();
        }

        movTypeService.deleteMovTypeById(movType_id, user_id);
        return new stringResponse("Deleted movement type");
    }

}
