package pojo;

import javax.persistence.*;
import java.io.Serializable;


@Entity(name = "attendance")
public class Attendance implements Serializable
{
    @EmbeddedId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private AttendanceID idattendance;
    private String attend;

    public Attendance() {
    }

    public AttendanceID getIdstudent() {
        return idattendance;
    }

    public void setIdattendance(AttendanceID idattendance) {
        this.idattendance = idattendance;
    }

    public String getAttend() {
        return attend;
    }

    public void setAttend(String attend) {
        this.attend = attend;
    }

    public AttendanceID getIdattendance() {
        return idattendance;
    }

//    public Subject getSubject() {
//        return subject;
//    }

//    public void setSubject(Subject subject) {
//        this.subject = subject;
//    }
}
