package app.repbook.movement;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(indexes = {
        @Index(name = "movement_id_idx", columnList = "movement_id DESC, id ASC")
})
public class Sset {
    @Id
    @SequenceGenerator(
            name = "sset_id_sequence",
            sequenceName = "sset_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "sset_id_sequence"
    )
    private Integer id;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movement_id", nullable = false)
    private Movement movement;
    @NotNull
    @Min(-2)
    @Max(999)
    @Column(nullable = false)
    private Integer reps;
    @NotNull
    @Min(-1)
    @Max(9999)
    @Column(nullable = false)
    private BigDecimal weight;
    @NotNull
    @Min(-1)
    @Max(99)
    @Column(nullable = false)
    private Integer mm;
    @NotNull
    @Min(-1)
    @Max(59)
    @Column(nullable = false)
    private Integer ss;

    public Sset() {}

    public Sset(Integer reps, BigDecimal weight, Integer mm, Integer ss) {
        this.reps = reps;
        this.weight = weight;
        this.mm = mm;
        this.ss = ss;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Movement getMovement() {
        return movement;
    }

    public void setMovement(Movement movement) {
        this.movement = movement;
    }

    public Integer getReps() {
        return reps;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public Integer getMm() {
        return mm;
    }

    public void setMm(Integer mm) {
        this.mm = mm;
    }

    public Integer getSs() {
        return ss;
    }

    public void setSs(Integer ss) {
        this.ss = ss;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sset sset = (Sset) o;
        return Objects.equals(id, sset.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Sset{" +
                "id=" + id +
                ", movement=" + movement +
                ", reps=" + reps +
                ", weight=" + weight +
                ", mm=" + mm +
                ", ss=" + ss +
                '}';
    }
}
