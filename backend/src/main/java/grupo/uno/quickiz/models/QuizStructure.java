package grupo.uno.quickiz.models;

import java.util.*;
import javax.persistence.*;
import java.io.Serializable;

import grupo.uno.quickiz.models.Quiz;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "quiz_structures")
@SuppressWarnings("serial")
public class QuizStructure implements Serializable {

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

    @Column(name = "duration")
    private Integer duration; 

    @Column(name = "slacktime")
    private Integer slacktime; 

    // @JsonManagedReference(value="questionsStructures")
    @OneToMany(mappedBy = "quizStructure", fetch = FetchType.EAGER)
    private List<QuestionStructure> questionsStructures = new ArrayList<QuestionStructure>();

    // @JsonManagedReference(value="quizzes")
    @JsonIgnore
    @OneToMany(mappedBy = "quizStructure")
    private List<Quiz> quizzes = new ArrayList<>();
    
    public QuizStructure() {
	}
    
    public QuizStructure(String name, Integer duration, Integer slacktime) {
		this.name = name;
		this.duration = duration;
		this.slacktime = slacktime;
	}

    public List<Quiz> getQuizzes() {
        return this.quizzes;
    }

    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getSlackTime() {
        return slacktime;
    }

    public void setSlackTime(Integer slacktime) {
        this.slacktime = slacktime;
    }

	public List<QuestionStructure> getQuestionsStructures() {
		return questionsStructures;
	}

	public void setQuestionsStructures(List<QuestionStructure> questionsStructures) {
		this.questionsStructures = questionsStructures;
	}
}

