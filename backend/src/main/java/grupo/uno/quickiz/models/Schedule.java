package grupo.uno.quickiz.models;

import javax.persistence.*;
import java.sql.*;

@Entity
@Table(name = "schedules")
public class Schedule {

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

    @Column(name = "code")
    private String code;

    @Column(name = "start_at")
    @Basic
    private Timestamp startAt;

    @Column(name = "finish_at")
    @Basic
    private Timestamp finishAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

}
