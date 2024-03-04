package app.repbook.movement;


import java.util.List;

public class MovementDTO {
    private Integer id;
    private String movType;
    private Integer restAfter;
    private String notes;
    private List<SsetDTO> ssetList;

    public MovementDTO(Integer id, String movType, Integer restAfter, String notes, List<SsetDTO> ssetListDTO) {
        this.id = id;
        this.movType = movType;
        this.restAfter = restAfter;
        this.notes = notes;
        this.ssetList = ssetListDTO;
    }

    public MovementDTO(Movement movement) {
        this(
                movement.getId(),
                movement.getMovType(),
                movement.getRestAfter(),
                movement.getNotes(),
                movement.getSsetDTOList()
        );
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

    public List<SsetDTO> getSsetList() {
        return ssetList;
    }

    public void setSsetList(List<SsetDTO> ssetList) {
        this.ssetList = ssetList;
    }


}
