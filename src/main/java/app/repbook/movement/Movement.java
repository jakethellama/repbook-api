package app.repbook.movement;

import app.repbook.workout.Workout;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(indexes = {
        @Index(name = "workout_id_idx", columnList = "workout_id DESC, id ASC")
})
public class Movement {
    @Id
    @SequenceGenerator(
            name = "movement_id_sequence",
            sequenceName = "movement_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "movement_id_sequence"
    )
    private Integer id;
    @Size(max=40)
    @NotNull
    @Column(nullable = false)
    private String movType;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_id", nullable = false)
    private Workout workout;
    @NotNull
    @Min(-1)
    @Max(9999)
    @Column(nullable = false)
    private Integer restAfter;
    @NotNull
    @Size(max=100)
    @Column(nullable = false)
    private String notes;
    @NotNull
    @Size(min=0, max=20)
    @OrderBy("id ASC")
    @OneToMany(mappedBy = "movement", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Sset> ssetList;

    public Movement() {}

    public Movement(String movType, Integer restAfter, String notes, List<Sset> ssetList) {
        this.movType = movType;
        this.restAfter = restAfter;
        this.notes = notes;
        this.ssetList = ssetList;
    }

    public void addSset(Sset sset) {
        ssetList.add(sset);
        sset.setMovement(this);
    }

    public void removeSset(Sset sset) {
        ssetList.remove(sset);
        sset.setMovement(null);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMovType() {
        return movType;
    }

    public void setMovType(String movType) {
        this.movType = movType;
    }

    public Workout getWorkout() {
        return workout;
    }

    public void setWorkout(Workout workout) {
        this.workout = workout;
    }

    public Integer getRestAfter() {
        return restAfter;
    }

    public void setRestAfter(Integer restAfter) {
        this.restAfter = restAfter;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Sset> getSsetList() {
        return ssetList;
    }

    public List<SsetDTO> getSsetDTOList() {
        List<SsetDTO> ssetDTOList = new ArrayList<>();

        for (Sset s : ssetList) {
            ssetDTOList.add(new SsetDTO(s));
        }

        return ssetDTOList;
    }

    public void setSsetList(List<Sset> ssetList) {
        this.ssetList = ssetList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movement movement = (Movement) o;
        return Objects.equals(id, movement.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Movement{" +
                "id=" + id +
                ", movType=" + movType +
                ", workout=" + workout +
                ", restAfter=" + restAfter +
                ", notes='" + notes + '\'' +
                '}';
    }
}
