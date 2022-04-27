package dao;

import pojo.Account;
import utils.HibernateUtil;
import org.hibernate.Session;

import com.google.common.hash.Hashing;
import org.hibernate.Transaction;

import java.nio.charset.StandardCharsets;


public class accountDAO {
    public static Account logIn(String username, String password)
    {
        Session session=HibernateUtil.getCurrentSession();
        HibernateUtil.openTransaction();
        Account account=(Account)session.get(Account.class,username);
        if(account!=null)
        {
            String hashedPassword=hashPassword(password);
            if(hashedPassword.equals(account.getPassword())==false)
            {
                return null;
            }
        }
        return account;
    }

    public static boolean createNewStudent(String username, String password)
    {
        Account account=new Account();
        account.setUsername(username);
        account.setPassword(hashPassword(password));
        account.setIsadmin(true);
        account.setIsactive(true);

        Session session=HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction trans=HibernateUtil.openTransaction();
        try{
            session.save(account);
            trans.commit();
        }catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
    public static void createNewAdmin(String studentID)
    {
        Account account=new Account();
        account.setUsername(studentID);
        account.setPassword(hashPassword(studentID));
        account.setIsactive(false);
        account.setIsadmin(false);

        Session session=HibernateUtil.getCurrentSession();
        Transaction trans=HibernateUtil.openTransaction();
        session.saveOrUpdate(account);
        trans.commit();
    }

    public static void updateStudent(String studentID)
    {
        Session session=HibernateUtil.getCurrentSession();
        Transaction trans=HibernateUtil.openTransaction();
        Account account=(Account) session.get(Account.class,studentID);
        if(account==null)
            createNewAdmin(studentID);

    }

    public static String hashPassword(String password)
    {
        return Hashing.sha256().hashString(password,StandardCharsets.UTF_8).toString();
    }

    public static void updatePass(Account account, String newPassword)
    {
        account.setPassword(accountDAO.hashPassword(newPassword));
        account.setIsactive(true);
        Session session=HibernateUtil.getCurrentSession();
        Transaction trans=HibernateUtil.openTransaction();
        session.saveOrUpdate(account);
        trans.commit();
        System.out.println(session.getTransaction().getStatus());

    }
}
