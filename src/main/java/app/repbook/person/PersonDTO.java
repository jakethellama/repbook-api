package app.repbook.person;

public class PersonDTO {
    private Integer id;
    private String username;

    public PersonDTO(Integer id, String username) {
        this.id = id;
        this.username = username;
    }

    public PersonDTO(Person person) {
        this(person.getId(), person.getUsername());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
