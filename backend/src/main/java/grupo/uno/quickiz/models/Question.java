package grupo.uno.quickiz.models;

import java.util.*;
import javax.persistence.*;
import java.io.Serializable;

import grupo.uno.quickiz.models.Pool;
import grupo.uno.quickiz.models.User;
import grupo.uno.quickiz.models.QuestionInstance;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "questions")
@SuppressWarnings("serial")
//@JsonIgnoreProperties(ignoreUnknown = true)
public class Question implements Serializable {

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
    
    @Column(name = "score")
    private Integer score;

    @Column(name = "title")
    private String title;

    @Column(name = "code")
    private String code;

    @Column(name = "variables")
    private String variables; //Json

    @JsonBackReference(value = "user-question")
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @JsonBackReference(value = "pool-question")
    @ManyToOne
    @JoinColumn(name="pool_id")
    private Pool pool;

    //@JsonBackReference(value = "question-questioninstance")
    //@JsonIgnoreProperties
    // @JsonManagedReference(value = "question-questioninstance")
    @JsonIgnore
    @OneToMany(mappedBy = "question")
    private List<QuestionInstance> questionInstances = new ArrayList<>();
    
    @JsonBackReference(value = "question-quizstructure")
    @ManyToOne
    @JoinColumn(name="quiz_structure_id")
    private QuizStructure quizStructure;

    // private transient Integer poolId;
    
    // private transient Integer quizStructureId;

    // private transient Integer userId;
    
    public Question() {
    	
    }
    
    public Question(Integer score, String title, String code, String variables, Pool pool) {
    	this.score = score;
    	this.title = title;
    	this.code = code;
    	this.variables = variables;
    	this.pool = pool;
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getVariables() {
		return variables;
	}

	public void setVariables(String variables) {
		this.variables = variables;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Pool getPool() {
		return pool;
	}

	public void setPool(Pool pool) {
		this.pool = pool;
	}

	public List<QuestionInstance> getQuestionInstances() {
		return questionInstances;
	}

	public void setQuestionInstances(List<QuestionInstance> questionInstances) {
		this.questionInstances = questionInstances;
	}

	public QuizStructure getQuizStructure() {
		return quizStructure;
	}

	public void setQuizStructure(QuizStructure quizStructure) {
		this.quizStructure = quizStructure;
	}

	// public Integer getPoolId() {
	// 	return poolId;
	// }

	// public void setPoolId(Integer poolId) {
	// 	this.poolId = poolId;
	// }

	// public Integer getQuizStructureId() {
	// 	return quizStructureId;
	// }

	// public void setQuizStructureId(Integer quizStructureId) {
	// 	this.quizStructureId = quizStructureId;
	// }

	// public Integer getUserId() {
	// 	return userId;
	// }

	// public void setUserId(Integer userId) {
	// 	this.userId = userId;
	// }

    
}



