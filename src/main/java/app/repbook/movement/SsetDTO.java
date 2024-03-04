package app.repbook.movement;

import java.math.BigDecimal;

public class SsetDTO {
    private Integer id;
    private Integer reps;
    private BigDecimal weight;
    private Integer mm;
    private Integer ss;

    public SsetDTO(Integer id, Integer reps, BigDecimal weight, Integer mm, Integer ss) {
        this.id = id;
        this.reps = reps;
        this.weight = weight;
        this.mm = mm;
        this.ss = ss;
    }

    public SsetDTO(Sset sset) {
        this(
                sset.getId(),
                sset.getReps(),
                sset.getWeight(),
                sset.getMm(),
                sset.getSs()
        );
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
