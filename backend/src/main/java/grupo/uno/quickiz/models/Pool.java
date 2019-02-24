package grupo.uno.quickiz.models;

import java.util.*;
import javax.persistence.*;
import java.io.Serializable;
import grupo.uno.quickiz.models.User;
import grupo.uno.quickiz.models.Question;
import grupo.uno.quickiz.models.QuestionStructure;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "pools")
@SuppressWarnings("serial")
public class Pool implements Serializable{

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

    @JsonBackReference(value = "user-pool")
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    // @JsonManagedReference(value = "pool-question")
    @JsonIgnore
    @OneToMany(mappedBy = "pool")
    private List<Question> questions = new ArrayList<>();

    // @JsonManagedReference(value = "pool-questionstructure")
    @JsonIgnore
    @OneToMany(mappedBy = "pool")
    private List<QuestionStructure> questionStructures = new ArrayList<>();

    private transient Integer userId;
    
    public Pool(){
    	
    }
    
    public Pool(String name){
    	this.name = name;
    }
    
    public List<Question> getQuestions() {
        return this.questions;
    }

    public List<QuestionStructure> getQuestionStructures() {
        return this.questionStructures;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void setQuestionStructures(List<QuestionStructure> questionStructures) {
        this.questionStructures = questionStructures;
    }

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
    


}

