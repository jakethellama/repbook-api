package app.repbook.movement;

import app.repbook.person.Person;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Objects;

@Entity
public class MovType {
    @Id
    @SequenceGenerator(
            name = "movtype_id_sequence",
            sequenceName = "movtype_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "movtype_id_sequence"
    )
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_username", referencedColumnName="username")
    private Person person;
    @Size(max=50)
    @NotNull
    @Column(nullable = false)
    private String name;

    public MovType() {}

    public MovType(Person person, String name) {
        this.person = person;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MovType movType = (MovType) o;
        return Objects.equals(id, movType.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "MovType{" +
                "id=" + id +
                ", person=" + person +
                ", name='" + name + '\'' +
                '}';
    }
}
