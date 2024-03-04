package app.repbook.movement;

import app.repbook.BadRequestException;
import app.repbook.ForbiddenException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import app.repbook.person.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class SsetServiceImpl implements SsetService {
    private final SsetRepository ssetRepository;
    private final PersonRepository personRepository;
    private final MovementRepository movementRepository;
    private final Validator validator;

    @Autowired
    public SsetServiceImpl(SsetRepository ssetRepository, PersonRepository personRepository, MovementRepository movementRepository, Validator validator) {
        this.ssetRepository = ssetRepository;
        this.personRepository = personRepository;
        this.movementRepository = movementRepository;
        this.validator = validator;
    }

    @Override
    public SsetDTO createSset(Integer movement_id, Integer user_id) {
        if (!personRepository.existsById(user_id)) {
            throw new BadRequestException();
        }

        if (movement_id == null) {throw new BadRequestException();}
        Optional<Movement> movement = movementRepository.findById(movement_id);
        if (movement.isEmpty()) {
            throw new BadRequestException();
        } else if (!Objects.equals(movement.get().getWorkout().getPerson().getId(), user_id)) {
            throw new ForbiddenException();
        }

        Sset sset = new Sset(-1, BigDecimal.valueOf(-1), -1, -1);
        movement.get().addSset(sset);

        if (!validMovementEntity(movement.get()) || movement.get().getSsetList().size() > 20) {
            throw new BadRequestException();
        }

        Movement upMovement = movementRepository.save(movement.get());
        return new SsetDTO(upMovement.getSsetList().getLast());
    }

    @Override
    public SsetDTO patchSsetById(Integer sset_id, Integer reps, BigDecimal weight, Integer mm, Integer ss, Integer user_id) {
        Optional<Sset> sset = ssetRepository.findById(sset_id);
        if (sset.isEmpty()) {
            throw new SsetDoesNotExistException();
        } else if (!Objects.equals(sset.get().getMovement().getWorkout().getPerson().getId(), user_id)) {
            throw new ForbiddenException();
        }

        if (reps != null) {sset.get().setReps(reps);}
        if (weight != null) {sset.get().setWeight(weight);}
        if (mm != null) {sset.get().setMm(mm);}
        if (ss != null) {sset.get().setSs(ss);}

        if (!validSsetEntity(sset.get())) {
            throw new BadRequestException();
        }

        Sset upSset = ssetRepository.save(sset.get());
        return new SsetDTO(upSset);
    }

    @Override
    public void deleteSsetById(Integer sset_id, Integer user_id) {
        Optional<Sset> sset = ssetRepository.findById(sset_id);
        if (sset.isEmpty()) {
            throw new SsetDoesNotExistException();
        } else if (!Objects.equals(sset.get().getMovement().getWorkout().getPerson().getId(), user_id)) {
            throw new ForbiddenException();
        }

        Movement movement = sset.get().getMovement();
        movement.removeSset(sset.get());

        movementRepository.save(movement);
    }

    private boolean validSsetEntity(Sset sset) {
        Set<ConstraintViolation<Sset>> violations = validator.validate(sset);
        return violations.isEmpty();
    }

    private boolean validMovementEntity(Movement movement) {
        Set<ConstraintViolation<Movement>> violations = validator.validate(movement);
        return violations.isEmpty();
    }
}
