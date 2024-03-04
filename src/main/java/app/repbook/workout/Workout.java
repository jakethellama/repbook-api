package app.repbook.workout;

import app.repbook.movement.Movement;
import app.repbook.movement.MovementDTO;
import app.repbook.person.Person;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(indexes = {
        @Index(name = "username_date_id_idx", columnList = "person_username ASC, date DESC, id DESC")
})
public class Workout {
    @Id
    @SequenceGenerator(
            name = "workout_id_sequence",
            sequenceName = "workout_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "workout_id_sequence"
    )
    private Integer id;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_username", nullable = false, referencedColumnName="username")
    private Person person;
    @NotNull
    @Size(max=40)
    @Column(nullable = false)
    private String name;
    @NotNull
    @Column(nullable = false)
    private LocalDate date;

    @NotNull
    @Size(max=200)
    @Column(nullable = false)
    private String notes;

    @NotNull
    @Size(min=0, max=20)
    @OrderBy("id ASC")
    @OneToMany(mappedBy = "workout", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Movement> movementList;

    public Workout() {}

    public Workout(String name, LocalDate date, String notes, Person person, List<Movement> movementList) {
        this.name = name;
        this.date = date;
        this.notes = notes;
        this.person = person;
        this.movementList = movementList;
    }

    public void addMovement(Movement movement) {
        movementList.add(movement);
        movement.setWorkout(this);
    }

    public void removeMovement(Movement movement) {
        movementList.remove(movement);
        movement.setWorkout(null);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public List<Movement> getMovementList() {
        return movementList;
    }

    public List<MovementDTO> getMovementDTOList() {
        List<MovementDTO> movementDTOList = new ArrayList<>();

        for (Movement mt : movementList) {
            movementDTOList.add(new MovementDTO(mt));
        }

        return movementDTOList;
    }

    public void setMovementList(List<Movement> movementList) {
        this.movementList = movementList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Workout workout = (Workout) o;
        return Objects.equals(id, workout.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Workout{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date=" + date +
                ", notes='" + notes + '\'' +
                ", person=" + person +
                '}';
    }
}
