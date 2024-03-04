package app.repbook.movement;

import app.repbook.UnauthorizedException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api")
public class SsetController {
    private final SsetServiceImpl ssetService;


    @Autowired
    public SsetController(SsetServiceImpl ssetService) {
        this.ssetService = ssetService;
    }

    public record postSsetRequest(Integer movement_id) {}
    public record stringResponse(String message) {}

    @PostMapping("/sets")
    public SsetDTO postSset(HttpSession session, @RequestBody postSsetRequest req) {
        Integer user_id = (Integer) session.getAttribute("user_id");

        if (user_id == null) {
            throw new UnauthorizedException();
        }

        SsetDTO newSset = ssetService.createSset(req.movement_id(), user_id);
        return newSset;
    }

    public record patchSsetRequest(Integer reps, BigDecimal weight, Integer mm, Integer ss) {}

    @PatchMapping("/sets/{sset_id}")
    public SsetDTO patchSsetById(HttpSession session, @PathVariable Integer sset_id, @RequestBody patchSsetRequest req) {
        Integer user_id = (Integer) session.getAttribute("user_id");

        if (user_id == null) {
            throw new UnauthorizedException();
        }

        SsetDTO upSset = ssetService.patchSsetById(sset_id, req.reps(), req.weight(), req.mm(), req.ss(), user_id);
        return upSset;
    }

    @DeleteMapping("/sets/{sset_id}")
    public stringResponse deleteSset(HttpSession session, @PathVariable Integer sset_id) {
        Integer user_id = (Integer) session.getAttribute("user_id");

        if (user_id == null) {
            throw new UnauthorizedException();
        }

        ssetService.deleteSsetById(sset_id, user_id);
        return new stringResponse("Deleted set");
    }


}
