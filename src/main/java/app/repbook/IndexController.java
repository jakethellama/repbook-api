package app.repbook;

import app.repbook.person.PersonDTO;
import app.repbook.person.PersonServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class IndexController {
    private final PersonServiceImpl personService;

    @Autowired
    public IndexController(PersonServiceImpl personService) {
        this.personService = personService;
    }

    public record userPassRequest(String username, String password) {}
    public record stringResponse(String message) {}

    @GetMapping("")
    public stringResponse getIndex() {
        return new stringResponse("Welcome to RepBook API!");
    }

    public record authCheckResponse(Boolean isAuth, String username) {}

    @GetMapping("/authCheck")
    public authCheckResponse getAuthCheck(HttpSession session) {
        Integer user_id = (Integer) session.getAttribute("user_id");

        return new authCheckResponse(user_id != null, (String) session.getAttribute("username"));
    }


    @PostMapping("/login")
    public stringResponse postLogin(HttpSession session, @RequestBody userPassRequest req) {
        Integer user_id = (Integer) session.getAttribute("user_id");

        if (user_id != null) {
            throw new ForbiddenException();
        }

        PersonDTO person = personService.getPersonIdByLogin(req.username(), req.password());
        session.setAttribute("user_id", person.getId());
        session.setAttribute("username", person.getUsername());
        return new stringResponse("Logged in");
    }

    @PostMapping("/logout")
    public stringResponse postLogout(HttpSession session) {
        Integer user_id = (Integer) session.getAttribute("user_id");

        if (user_id == null) {
            throw new UnauthorizedException();
        }

        session.invalidate();
        return new stringResponse("Logged Out");
    }
}
