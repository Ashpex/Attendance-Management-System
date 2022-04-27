package utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.resource.transaction.spi.TransactionStatus;


public class HibernateUtil {
    private static SessionFactory sessionFactory;
    public static void config()
    {
        if(sessionFactory==null)
        {
            try {
                Configuration configuration=new Configuration();
                configuration.configure("hibernate.cfg.xml");
                sessionFactory=configuration.buildSessionFactory();

            }catch (Exception e)
            {
                System.out.println("ERROR: error in initializing Session Factory!");
            }
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Session getCurrentSession()
    {
        return sessionFactory.getCurrentSession();
    }

    public static Transaction openTransaction()
    {
        Session session=sessionFactory.getCurrentSession();
        Transaction trans;
        if(session.getTransaction().getStatus()== TransactionStatus.NOT_ACTIVE)
        {
            trans=session.beginTransaction();
        }
        else
        {
            trans=session.getTransaction();
        }
        return trans;
    }
}
