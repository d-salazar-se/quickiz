package grupo.uno.quickiz.models;

import java.util.*;
import javax.persistence.*;
import java.io.Serializable;
import grupo.uno.quickiz.models.Pool;
import grupo.uno.quickiz.models.Role;
import grupo.uno.quickiz.models.Question;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "users")
@SuppressWarnings("serial")
public class User implements Serializable {

    @Id
    @GeneratedValue(
    strategy = GenerationType.IDENTITY
    )
    @Column(
    name = "id",
    unique = true,
    nullable = false
    )
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @JsonManagedReference(value = "user-pool")
    @OneToMany(mappedBy = "user")
    private List<Pool> pools = new ArrayList<>();

    @JsonManagedReference(value = "user-question")
    @OneToMany(mappedBy = "user")
    private List<Question> questions = new ArrayList<>();

    // @JsonManagedReference(value = "user-role")
    @ManyToOne
    @JoinColumn(name="role_id")
    private Role role;

    public List<Pool> getPools() {
        return this.pools;
    }

    public void setPools(List<Pool> pools) {
        this.pools = pools;
    }

    public List<Question> getQuestions() {
        return this.questions;
    }

    public void setQuestion(List<Question> questions) {
        this.questions = questions;
    }

    public Role getRole() {
        return this.role;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
