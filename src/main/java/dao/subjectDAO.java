package dao;

import pojo.Subject;
import utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class subjectDAO {


    public static Subject getSubject(String subjectID)
    {
        Session session= HibernateUtil.getCurrentSession();
        HibernateUtil.openTransaction();
        return (Subject) session.get(Subject.class,subjectID);
    }
    public static void removeSubject(Subject subject)
    {
        //delete attendance first
        attendanceDAO.removeStudentFromClass(subject.getIdsubject());
        Session session= HibernateUtil.getCurrentSession();
        Transaction trans=HibernateUtil.openTransaction();
        String subjectID=subject.getIdsubject();
        try {
            session.createQuery("delete from Subject where idsubject=" +
                    "'" +subjectID+ "'").executeUpdate();
            trans.commit();
        }
        catch (Exception e)
        {
            trans.rollback();
            e.printStackTrace();
        }

    }

    public static boolean saveOrUpdateSubject(Subject subject)
    {
        Session session=HibernateUtil.getCurrentSession();
        Transaction trans=HibernateUtil.openTransaction();
        Subject existedSubject=getSubject(subject.getIdsubject());
        try{
            if(existedSubject!=null)
            {
                session.merge(subject);
            }
            else {
                session.save(subject);
            }

            trans.commit();
            return true;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            trans.rollback();
            return false;
        }
    }
    public static List<Subject> getAllSubjects()
    {
        List<Subject> ret;
        Session session=HibernateUtil.getCurrentSession();
        HibernateUtil.openTransaction();
        ret=session.createQuery("from Subject s").list();
        System.out.println(ret.size());
        return ret;
    }

    public static List<Object[]> convertToListObject(List<Subject>subjectList)
    {
        List<Object[]> ret=new ArrayList();
        if(subjectList!=null)
        {
            for (int i=0;i<subjectList.size();i++)
            {
                Subject s=subjectList.get(i);
                Object[] rowData={s.getIdsubject(),s.getSubjectName(),s.getSchedule(),s.getRoomname(),s.getNumberweeks(),s.getStartday(),s.getEndday()};
                ret.add(rowData);
            }
        }
        return ret;
    }

    public static Object[] convertSubjectToRowData(Subject subject)
    {
        return new Object[]{subject.getIdsubject(),subject.getSubjectName(),
                subject.getSchedule(),subject.getRoomname(),
                subject.getNumberweeks(),
                subject.getStartday(),
                subject.getEndday()
        };
    }

    public static ArrayList<String> getAllSubjectNames(List<Subject>subjects)
    {
        ArrayList<String> ret=new ArrayList<>();
        for(int i=0;i<subjects.size();i++)
        {
            ret.add(subjects.get(i).getSubjectName());
        }
        return ret;
    }

    public static List<Subject> getSubjectByName(String subjectName)
    {
        Session session=HibernateUtil.getCurrentSession();
        List<Subject> subject=(List<Subject>) session.createQuery("from Subject where subjectName='"+subjectName+"'",Subject.class).list();
        return subject;
    }

    public static java.util.Date convertSQLDateToUtilDate(java.sql.Date date)
    {
        return new java.util.Date(date.getTime());
    }

    public static int convertScheduleToObject(String schedule,int pos)
    {
        schedule=schedule.strip();
        String[] objects=schedule.split("\\(");
        if(objects.length<2)
            return -1;
        String[] sessions=objects[1].substring(0,objects[1].length()-1).split("-");

        objects[0]=objects[0].strip();
        switch (pos)
        {
            case 1:
                return Integer.parseInt(sessions[0].strip());
            case 2:
                return Integer.parseInt(sessions[1].strip());
            default:
                if(objects[0].equals("Mon"))
                    return 0;
                if(objects[0].equals("Tue"))
                    return 1;
                if(objects[0].equals("Wed"))
                    return 2;
                if(objects[0].equals("Thu"))
                    return 3;
                if(objects[0].equals("Fri"))
                    return 4;
                if(objects[0].equals("Sat"))
                    return 5;
                if(objects[0].equals("Sun"))
                    return 6;
        }
        return -1;
    }


    public static int isInSession(java.util.Date today,int startSession,int endSession)
    {
        java.util.Date emptyDay=new Date(today.getTime());
        emptyDay.setHours(7);
        emptyDay.setMinutes(0);
        emptyDay.setSeconds(0);
        java.util.Date startTime=new java.util.Date(emptyDay.getTime()+convertSessionFromFirstSessionToTime(startSession)*60*1000);
        java.util.Date endTime=new java.util.Date(emptyDay.getTime()+(convertSessionFromFirstSessionToTime(endSession)+50)*60*1000);

        if(today.before(new java.util.Date(emptyDay.getTime()+convertSessionFromFirstSessionToTime(startSession)*60*1000)))
            return -1;


        System.out.println(startTime);
        System.out.println(today);
        System.out.println(endTime);
        if(today.after(
                new java.util.Date(
                emptyDay.getTime()+
                (convertSessionFromFirstSessionToTime(endSession)+50)*60*1000
        )))
            return 1;
        return 0;

    }

    public static int convertSessionFromFirstSessionToTime(int session)
    {


        if(session<=5)
        {
            if(session==1)
                return 0;
            return 45*(session-1)+5*(session-2);
        }
        else
        {
            if(session==6)
                return 330;

            return 330+45*(session-6)+5*(session-7);
        }

    }
}
