package app.repbook.workout;

import app.repbook.BadRequestException;
import app.repbook.ForbiddenException;
import app.repbook.movement.MovementRepository;
import app.repbook.person.Person;
import app.repbook.person.PersonDoesNotExistException;
import app.repbook.person.PersonRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.jsoup.nodes.Entities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
public class WorkoutServiceImpl implements WorkoutService {
    private final WorkoutRepository workoutRepository;
    private final PersonRepository personRepository;
    private final MovementRepository movementRepository;
    private final Validator validator;

    @Autowired
    public WorkoutServiceImpl(WorkoutRepository workoutRepository, PersonRepository personRepository, MovementRepository movementRepository, Validator validator) {
        this.workoutRepository = workoutRepository;
        this.personRepository = personRepository;
        this.movementRepository = movementRepository;
        this.validator = validator;
    }

    @Override
    public WorkoutDTO createWorkout(String name, LocalDate date, Integer user_id) {
        Optional<Person> user = personRepository.findById(user_id);
        if (user.isEmpty()) {
            throw new BadRequestException();
        }

        if (name != null) {name = Entities.escape(name.strip());}

        Workout workout = new Workout(name, date, "", user.get(), new ArrayList<>());
        if (!validWorkoutEntity(workout)) {
            throw new BadRequestException();
        }

        Workout newWorkout = workoutRepository.save(workout);
        return new WorkoutDTO(newWorkout);
    }

    @Override
    public WorkoutDTO getWorkoutById(Integer workout_id, Integer user_id) {
        Optional<Workout> workout = workoutRepository.findById(workout_id);
        if (workout.isEmpty()) {
            throw new WorkoutDoesNotExistException();
        } else if (!Objects.equals(workout.get().getPerson().getId(), user_id)) {
            throw new ForbiddenException();
        }

        return new WorkoutDTO(workout.get());
    }

    @Override
    public WorkoutDTO patchWorkoutInfoById(Integer workout_id, String name, LocalDate date, String notes, Integer user_id) {
        Optional<Workout> workout = workoutRepository.findById(workout_id);
        if (workout.isEmpty()) {
            throw new WorkoutDoesNotExistException();
        } else if (!Objects.equals(workout.get().getPerson().getId(), user_id)) {
            throw new ForbiddenException();
        }

        if (name != null) {workout.get().setName(Entities.escape(name.strip()));}
        if (date != null) {workout.get().setDate(date);}
        if (notes != null) {workout.get().setNotes(Entities.escape(notes.strip()));}

        if (!validWorkoutEntity(workout.get())) {
            throw new BadRequestException();
        }

        Workout upWorkout = workoutRepository.save(workout.get());
        return new WorkoutDTO(upWorkout);
    }

    @Override
    public List<WorkoutDTO> getWorkoutsInfoByUsername(String username, Integer user_id) {
        Person person = new Person(username);
        if (!validPersonEntity(person)) {
            throw new BadRequestException();
        }

        Optional<Person> targetPerson = personRepository.findByUsername(username);
        if (targetPerson.isEmpty()) {
            throw new PersonDoesNotExistException();
        } else if (!Objects.equals(targetPerson.get().getId(), user_id)) {
            throw new ForbiddenException();
        }

        List<Workout> workouts = workoutRepository.findAllByPerson_UsernameOrderByDateDescIdDesc(username);
        List<WorkoutDTO> workoutDTOs = new ArrayList<>();
        
        for (Workout w: workouts) {
            workoutDTOs.add(new WorkoutDTO(w, true));
        }

        return workoutDTOs;
    }

    @Override
    public void deleteWorkoutById(Integer workout_id, Integer user_id) {
        Optional<Workout> workout = workoutRepository.findById(workout_id);
        if (workout.isEmpty()) {
            throw new WorkoutDoesNotExistException();
        } else if (!Objects.equals(workout.get().getPerson().getId(), user_id)) {
            throw new ForbiddenException();
        }

        workoutRepository.delete(workout.get());
    }

    private boolean validWorkoutEntity(Workout workout) {
        Set<ConstraintViolation<Workout>> violations = validator.validate(workout);
        return violations.isEmpty();
    }

    private boolean validPersonEntity(Person person) {
        Set<ConstraintViolation<Person>> violations = validator.validate(person);
        return violations.isEmpty();
    }
}
