package grupo.uno.quickiz.models;

import java.sql.*;
import java.util.*;
import javax.persistence.*;
import java.io.Serializable;

import grupo.uno.quickiz.designPatterns.IQuizObservable;
import grupo.uno.quickiz.designPatterns.QuizObserverImpl;
import grupo.uno.quickiz.models.QuizStructure;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "quizzes")
@SuppressWarnings("serial")
public class Quiz implements Serializable, IQuizObservable {

	private ArrayList<QuizObserverImpl> quizObserverImpls;
	
	public void setQuizObserverImpl(QuizObserverImpl quizObserverImpl) {
		this.quizObserverImpls.add(quizObserverImpl);
	}
	
	public void notifyQuiz(String studentName, Timestamp startAt) {
		setChange(studentName, startAt);
	}
	
	@Override
	public void setChange(String studentName, Timestamp startAt) {
		for(QuizObserverImpl observer : quizObserverImpls) {
			observer.update(studentName, startAt);
		}
	}
	
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
    
    @Column(name = "student_name")
    private String studentName;

    @Column(name = "start_at")
    @Basic
    private Timestamp startAt;

    @Column(name = "finish_at")
    @Basic
    private Timestamp finishAt;

    @Column(name = "score")
    @Basic
    private Integer score;

    // @JsonBackReference(value = "quizstructure-quiz")
    @ManyToOne
    @JoinColumn(name="quiz_structure_id")
    private QuizStructure quizStructure;
    
    // private transient Integer quizStructureId;

    // @JsonManagedReference(value = "quiz-questioninstance")
    // @JsonIgnore
    @OneToMany(mappedBy = "quiz", fetch = FetchType.LAZY)
    private List<QuestionInstance> questionInstances = new ArrayList<>();
    
    public Quiz(){
    	this.quizObserverImpls = new ArrayList<QuizObserverImpl>();
    }
    
    public Quiz(QuizStructure quizStructure) {
    	this.quizStructure = quizStructure;
    	this.quizObserverImpls = new ArrayList<QuizObserverImpl>();
    }
    
    public Quiz(Timestamp startAt, Timestamp finishAt, QuizStructure quizStructure) {
    	this.startAt = startAt;
    	this.finishAt = finishAt;
    	this.quizStructure = quizStructure;
    	this.quizObserverImpls = new ArrayList<QuizObserverImpl>();
    }
    
    
    public List<QuestionInstance> getQuestionInstances() {
        return this.questionInstances;
    }

    public void setQuestionInstances(List<QuestionInstance> questionInstances) {
        this.questionInstances = questionInstances;
    }

    public Integer getScore() {
        return this.score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getStartAt() {
        return startAt;
    }

    public void setStartAt(Timestamp startAt) {
        this.startAt = startAt;
    }

    public Timestamp getFinishAt() {
        return finishAt;
    }

    public void setFinishAt(Timestamp finishAt) {
        this.finishAt = finishAt;
    }

    public QuizStructure getQuizStructure() {
        return quizStructure;
    }

    public void setQuizStructure(QuizStructure quizStructure) {
        this.quizStructure = quizStructure;
    }

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public ArrayList<QuizObserverImpl> getQuizObserverImpls() {
		return quizObserverImpls;
	}

	public void setQuizObserverImpls(ArrayList<QuizObserverImpl> quizObserverImpls) {
		this.quizObserverImpls = quizObserverImpls;
	}

	// public Integer getQuizStructureId() {
	// 	return quizStructureId;
	// }

	// public void setQuizStructureId(Integer quizStructureId) {
	// 	this.quizStructureId = quizStructureId;
	// }
}

