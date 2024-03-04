package app.repbook.person;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.NaturalId;

import java.util.Objects;

@Entity
public class Credential {
    @Id
    @SequenceGenerator(
            name = "cred_id_sequence",
            sequenceName = "cred_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "cred_id_sequence"
    )
    private Integer id;
    @NotNull
    @NaturalId
    @Size(min=2, max=20)
    @Pattern(regexp="^[a-zA-Z0-9-_]+$")
    @Column(unique=true, nullable = false)
    private String username;
    @NotNull
    @Size(min=5, max=64) // max input = 64
    @Column(nullable = false)
    private String password;

    public Credential() {}

    public Credential(String username, String password) {
        this.username = username;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credential that = (Credential) o;
        return Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        return "Credential{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
