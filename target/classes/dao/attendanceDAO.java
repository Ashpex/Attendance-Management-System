package dao;

import pojo.Attendance;
import pojo.AttendanceID;
import utils.attendanceTable;
import utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class attendanceDAO
{

    public static ArrayList<Attendance> queryAttend(String subjectID)
    {

        Session session=HibernateUtil.getCurrentSession();
        HibernateUtil.openTransaction();
        ArrayList<Attendance> attendances=(ArrayList<Attendance>) session.createQuery("from attendance as att where att.idattendance.idsubject='"    +subjectID+"'", Attendance.class).list();

        return attendances;
    }



    public static ArrayList<Attendance> queryAttendStudent(String studentID)
    {

        Session session=HibernateUtil.getCurrentSession();
        HibernateUtil.openTransaction();
        ArrayList<Attendance> attendances=(ArrayList<Attendance>) session.createQuery("from attendance as att where att.idattendance.idstudent='"    +studentID+"'", Attendance.class).list();

        return attendances;
    }

    public static boolean addAttend(String studentID, String subjectID, String attend)
    {
        accountDAO.updateStudent(studentID);
        Session session=HibernateUtil.getCurrentSession();
        Transaction trans=HibernateUtil.openTransaction();
        AttendanceID attendanceID=new AttendanceID();
        attendanceID.setIdstudent(studentID);
        attendanceID.setIdsubject(subjectID);
        Attendance attendance=(Attendance) session.get(Attendance.class,attendanceID);
        if(attendance!=null)
            return true;
        try{
            attendance=new Attendance();
            attendance.setIdattendance(attendanceID);
            attendance.setAttend(attend);
            session.save(attendance);
            trans.commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            trans.rollback();
            return false;
        }
        return true;
    }
    public static boolean updateAttend(String studentID, String subjectID, String attend)
    {
        accountDAO.updateStudent(studentID);
        Session session=HibernateUtil.getCurrentSession();
        Transaction trans=HibernateUtil.openTransaction();
        System.out.println(attend);
        try{
            session.createQuery("update attendance set attend='" +attend+
                    "'"+" where idattendance.idstudent='" +studentID+
                    "'"+ " and idattendance.idsubject='" +subjectID+
                    "'").executeUpdate();
            trans.commit();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            trans.rollback();
            return false;
        }
        return true;
    }

    public static Object[] generateToRow(Attendance attendance, boolean addSubject)
    {
        String attendRes=attendance.getAttend();
        int attResLength=attendRes.length();
        if(attResLength<=15)
        {
            for(int i=0;i<15-attResLength;i++)
            {
                attendRes+="0";
            }
        }
        List res=new ArrayList();
        if(addSubject)
        {
            res.add(attendance.getIdattendance().getIdsubject());
        }
        else
            res.add(attendance.getIdattendance().getIdstudent());
        for(int i=0;i<attendRes.length();i++)
        {
            int attRes=Integer.parseInt(String.valueOf(attendRes.charAt(i)));
            String attStr="";
            switch (attRes)
            {
                case 0:
                    attStr= attendanceTable.ABSENT;
                    break;
                case 2:
                    attStr= attendanceTable.ATTEND;
                    break;
                default:
                    attStr= attendanceTable.NOT_CHECKED;

            }
            res.add(attStr);
        }



        return res.toArray();
    }

    public static void removeStudentFromClass(String subjectID)
    {
        Session session=HibernateUtil.getCurrentSession();
        Transaction trans=HibernateUtil.openTransaction();
        try{
            session.createQuery("delete from attendance where idattendance.idsubject='" +
                    subjectID+
                    "'").executeUpdate();

            trans.commit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            trans.rollback();

        }
    }

    public static void removeStudentFromClassNew(String subjectID, String studentID)
    {
        Session session=HibernateUtil.getCurrentSession();
        Transaction trans=HibernateUtil.openTransaction();
        try{
            session.createQuery("delete from attendance where idattendance.idsubject='" +
                    subjectID+
                    "'"+" and idattendance.idstudent='" +studentID+
                    "'").executeUpdate();

            trans.commit();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            trans.rollback();

        }
    }


    public static ArrayList getAllSubjectsStudentAttend(String studentID)
    {

        Session session=HibernateUtil.getCurrentSession();
        HibernateUtil.openTransaction();
        ArrayList attendances= (ArrayList) session.createQuery(
                "from attendance as att join Subject as s on att.idattendance.idstudent='"
                        +studentID+"' and att.idattendance.idsubject=s.idsubject ").getResultList();


        return attendances;
    }

}
