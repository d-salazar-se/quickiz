package grupo.uno.quickiz.models;

import javax.persistence.*;
import java.io.Serializable;
import grupo.uno.quickiz.models.Pool;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "question_structures")
@SuppressWarnings("serial")
public class QuestionStructure implements Serializable {

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
    
    // @JsonBackReference(value="questionsStructures")
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="quiz_structure_id")
    private QuizStructure quizStructure;

    // @JsonBackReference(value="quizzes")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="pool_id")
    private Pool pool;
    
    public QuestionStructure() {}
    
    public QuestionStructure(Integer score, Pool pool) {
		this.score = score;
		this.pool = pool;
	}
    
    public QuestionStructure(Integer score, Pool pool, QuizStructure quizStructure) {
		this.score = score;
		this.pool = pool;
		this.quizStructure = quizStructure;
	}

    public Pool getPool() {
        return this.pool;
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

    public void setPool(Pool pool) {
        this.pool = pool;
    }

	public QuizStructure getQuizStructure() {
		return quizStructure;
	}

	public void setQuizStructure(QuizStructure quizStructure) {
		this.quizStructure = quizStructure;
	}
}
