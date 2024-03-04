package app.repbook.movement;

import app.repbook.person.PersonDTO;

public class MovTypeDTO {
    private Integer id;
    private PersonDTO person;
    private String name;

    public MovTypeDTO(Integer id, PersonDTO personDTO, String name) {
        this.id = id;
        this.person = personDTO;
        this.name = name;
    }

    public MovTypeDTO(MovType movType) {
        this(
                movType.getId(),
                movType.getPerson() == null ? null : new PersonDTO(movType.getPerson()),
                movType.getName()
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
}
