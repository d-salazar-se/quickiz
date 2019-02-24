package grupo.uno.quickiz.models;

import java.util.*;
import javax.persistence.*;
import java.io.Serializable;
import grupo.uno.quickiz.models.User;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "roles")
@SuppressWarnings("serial")
public class Role implements Serializable {

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

    @Column(name = "description")
    private String description;

    // @JsonManagedReference(value = "user-role")
    @JsonBackReference(value = "user-role")
    @OneToMany(mappedBy = "role")
    private List<User> users = new ArrayList<>();


    public List<User> getUsers() {
        return this.users;
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


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }


    public void setUsers(List<User> users) {
        this.users = users;
    }

}
