package grupo.uno.quickiz.models;

import javax.persistence.*;
import java.io.Serializable;
import grupo.uno.quickiz.models.Quiz;
import grupo.uno.quickiz.models.Question;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "question_instance")
@SuppressWarnings("serial")
public class QuestionInstance implements Serializable {

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

    @JsonIgnore
    @Column(name = "answer")
    private String answer;

    @Column(name = "student_answer")
    private String studentAnswer;

    @Column(name = "variable_values")
    private String variableValues; 

    //@JsonManagedReference(value = "question-questioninstance")
    // @JsonBackReference(value = "question-questioninstance")
    // @JsonIgnore
    @ManyToOne
    @JoinColumn(name="question_id")
    private Question question;

    // @JsonBackReference(value = "quiz-questioninstance")
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="quiz_id")
    private Quiz quiz;
    
    // private transient Integer questionId;

    // private transient Integer quizId;
    
    public QuestionInstance() {
    	
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setStudentAnswer(String studentAnswer) {
        this.studentAnswer = studentAnswer;
    }

    public String getStudentAnswer() {
        return studentAnswer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getVariableValues() {
        return variableValues;
    }

    public void setVariableValues(String variableValues) {
        this.variableValues = variableValues;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

	// public Integer getQuestionId() {
	// 	return questionId;
	// }

	// public void setQuestionId(Integer questionId) {
	// 	this.questionId = questionId;
	// }

	// public Integer getQuizId() {
	// 	return quizId;
	// }

	// public void setQuizId(Integer quizId) {
	// 	this.quizId = quizId;
	// }
}
