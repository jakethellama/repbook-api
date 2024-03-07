package app.repbook.movement;

import app.repbook.BadRequestException;
import app.repbook.ForbiddenException;
import app.repbook.person.PersonRepository;
import app.repbook.workout.Workout;
import app.repbook.workout.WorkoutRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.jsoup.nodes.Entities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class MovementServiceImpl implements MovementService {
    private final MovementRepository movementRepository;
    private final PersonRepository personRepository;
    private final WorkoutRepository workoutRepository;
    private final SsetRepository ssetRepository;
    private final Validator validator;


    @Autowired
    public MovementServiceImpl(MovementRepository movementRepository, PersonRepository personRepository, WorkoutRepository workoutRepository, SsetRepository ssetRepository, Validator validator) {
        this.movementRepository = movementRepository;
        this.personRepository = personRepository;
        this.workoutRepository = workoutRepository;
        this.ssetRepository = ssetRepository;
        this.validator = validator;
    }

    @Override
    public MovementDTO createMovement(String movType, Integer workout_id, Integer user_id) {
        if (!personRepository.existsById(user_id)) {
            throw new BadRequestException();
        }

        if (workout_id == null) {throw new BadRequestException();}
        Optional<Workout> workout = workoutRepository.findById(workout_id);
        if (workout.isEmpty()) {
            throw new BadRequestException();
        } else if (!Objects.equals(workout.get().getPerson().getId(), user_id)) {
            throw new ForbiddenException();
        }

        if (movType != null) {movType = Entities.escape(movType.strip());}

        Movement movement = new Movement(movType, -1, "", new ArrayList<>());

        workout.get().addMovement(movement);

        if (!validMovementEntity(movement)) {
            throw new BadRequestException();
        } else if (!validWorkoutEntity(workout.get()) || workout.get().getMovementList().size() > 20) {
            throw new BadRequestException();
        }

        Sset set1 = new Sset(-1, BigDecimal.valueOf(-1), -1, -1);
        Sset set2 = new Sset(-1, BigDecimal.valueOf(-1), -1, -1);
        Sset set3 = new Sset(-1, BigDecimal.valueOf(-1), -1, -1);

        movement.addSset(set1);
        movement.addSset(set2);
        movement.addSset(set3);

        Workout upWorkout = workoutRepository.save(workout.get());
        return new MovementDTO(upWorkout.getMovementList().getLast());
    }

    @Override
    public MovementDTO patchMovementInfoById(Integer movement_id, String movType, String notes, Integer restAfter, Integer user_id) {
        Optional<Movement> movement = movementRepository.findById(movement_id);
        if (movement.isEmpty()) {
            throw new MovementDoesNotExistException();
        } else if (!Objects.equals(movement.get().getWorkout().getPerson().getId(), user_id)) {
            throw new ForbiddenException();
        }

        if (movType != null) {movement.get().setMovType(Entities.escape(movType.strip()));}
        if (notes != null) {movement.get().setNotes(Entities.escape(notes.strip()));}
        if (restAfter != null) {movement.get().setRestAfter(restAfter);}

        if (!validMovementEntity(movement.get())) {
            throw new BadRequestException();
        }

        Movement upMovement = movementRepository.save(movement.get());
        return new MovementDTO(upMovement);
    }

    @Override
    public void deleteMovementById(Integer movement_id, Integer user_id) {
        Optional<Movement> movement = movementRepository.findById(movement_id);
        if (movement.isEmpty()) {
            throw new MovementDoesNotExistException();
        } else if (!Objects.equals(movement.get().getWorkout().getPerson().getId(), user_id)) {
            throw new ForbiddenException();
        }

        Workout workout = movement.get().getWorkout();
        workout.removeMovement(movement.get());
        workoutRepository.save(workout);
    }

    private boolean validMovementEntity(Movement movement) {
        Set<ConstraintViolation<Movement>> violations = validator.validate(movement);
        return violations.isEmpty();
    }

    private boolean validWorkoutEntity(Workout workout) {
        Set<ConstraintViolation<Workout>> violations = validator.validate(workout);
        return violations.isEmpty();
    }
}
