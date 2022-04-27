package pojo;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class AttendanceID implements Serializable
{

    private String idstudent;
    private String idsubject;



    public AttendanceID() {
    }

    public String getIdstudent() {
        return idstudent;
    }

    public void setIdstudent(String idstudent) {
        this.idstudent = idstudent;
    }

    public String getIdsubject() {
        return idsubject;
    }

    public void setIdsubject(String idsubject) {
        this.idsubject = idsubject;
    }

    @Override
    public String toString() {
        return "AttendanceID{" +
                "idstudent='" + idstudent + '\'' +
                ", idcourse='" + idsubject + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
