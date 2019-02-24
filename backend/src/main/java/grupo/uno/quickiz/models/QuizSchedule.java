package grupo.uno.quickiz.models;

import javax.persistence.*;
import java.sql.*;
import java.util.*;
import java.io.Serializable;
import grupo.uno.quickiz.models.Quiz;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "quiz_schedules")
@SuppressWarnings("serial")
public class QuizSchedule implements Serializable {

    @Id
    @GeneratedValue(
            strategy = GenerationType.AUTO
    )
    @Column(
            name = "id",
            unique = true,
            nullable = false
    )
    private Integer id;

    @Column(name = "start_at")
    @Basic
    private Timestamp startAt;

    @Column(name = "finish_at")
    @Basic
    private Timestamp finishAt;

    @JsonBackReference(value = "quiz-quizschedule")
    @OneToOne
    @JoinColumn(name="quiz_id")
    private Quiz quiz;
    
    public QuizSchedule(Timestamp startAt, Timestamp finishAt, Quiz quiz) {
    	this.startAt = startAt;
        this.finishAt = finishAt;
        this.quiz = quiz;
    }
    public QuizSchedule() {

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

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
}

