package app.repbook.workout;

import app.repbook.movement.MovementDTO;
import app.repbook.person.PersonDTO;

import java.time.LocalDate;
import java.util.List;

public class WorkoutDTO {
    private Integer id;
    private PersonDTO person;
    private String name;
    private LocalDate date;
    private String notes;
    private List<MovementDTO> movementList;

    public WorkoutDTO(Integer id, PersonDTO personDTO, String name, LocalDate date, String notes, List<MovementDTO> movementDTOList) {
        this.id = id;
        this.person = personDTO;
        this.name = name;
        this.date = date;
        this.notes = notes;
        this.movementList = movementDTOList;
    }

    public WorkoutDTO(Workout workout) {
        this(
                workout.getId(),
                new PersonDTO(workout.getPerson()),
                workout.getName(),
                workout.getDate(),
                workout.getNotes(),
                workout.getMovementDTOList()
        );
    }

    public WorkoutDTO(Workout workout, Boolean noMovementList) {
        this(
                workout.getId(),
                new PersonDTO(workout.getPerson()),
                workout.getName(),
                workout.getDate(),
                workout.getNotes(),
                (noMovementList) ? null : workout.getMovementDTOList()
        );
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public PersonDTO getPerson() {
        return person;
    }

    public void setPerson(PersonDTO person) {
        this.person = person;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<MovementDTO> getMovementList() {
        return movementList;
    }

    public void setMovementList(List<MovementDTO> movementList) {
        this.movementList = movementList;
    }
}
