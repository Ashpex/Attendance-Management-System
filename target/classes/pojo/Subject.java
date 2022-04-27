package pojo;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name="subject")
public class Subject implements Serializable {
    @Id
    private String idsubject;
    private String subjectName;
    private Date startday;
    private Date endday;
    private String schedule;
    private String roomname;
    private int numberweeks;

    public String getIdsubject() {
        return idsubject;
    }

    public void setIdsubject(String idsubject) {
        this.idsubject = idsubject;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public Date getStartday() {
        return startday;
    }

    public void setStartday(Date startday) {
        this.startday = startday;
    }
    public void setStartday(java.util.Date startday) {
        java.sql.Date newStartDay=new java.sql.Date(startday.getTime());
        this.startday = newStartDay;
    }
    public void setEndday(java.util.Date endday) {
        java.sql.Date newEndDay=new java.sql.Date(endday.getTime());
        this.endday = newEndDay;
    }

    public Date getEndday() {
        return endday;
    }

    public void setEndday(Date endday) {
        this.endday = endday;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public String getRoomname() {
        return roomname;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }

    public int getNumberweeks() {
        return numberweeks;
    }

    public void setNumberweeks(int numberweeks) {
        this.numberweeks = numberweeks;
    }
    //    public List<Attendance> getAttendances() {
//        return attendances;
//    }
//
//    public void setAttendances(List<Attendance> attendances) {
//        this.attendances = attendances;
//    }


    @Override
    public String toString() {
        return "Subject{" +
                "idsubject='" + idsubject + '\'' +
                ", subjectName='" + subjectName + '\'' +
                ", startday=" + startday +
                ", endday=" + endday +
                ", schedule='" + schedule + '\'' +
                ", roomname='" + roomname + '\'' +
                ", numberweeks=" + numberweeks +
                '}';
    }

    public static String convertDataToSchedule(String[] data)
    {
        /*
        data: Array of day in week, start session and end session
        data={diw,ss,es}
        */
        String ret="";
        if(data.length!=3)
            return ret;
        ret+=data[0].substring(0,3);
        ret+=" ("+data[1]+" - "+data[2]+")";
        return ret;
    }
}
