/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import org.hibernate.CacheMode;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

/**
 *
 * @author е
 */

public class HibUtil {

private static final SessionFactory sessionFactory;
private static final Session session;

    static {
        try {
            sessionFactory = new AnnotationConfiguration()
                    //.addPackage("entity")
                    .addAnnotatedClass(entity.Supply.class)
                    .addAnnotatedClass(entity.Product.class)
                    .addAnnotatedClass(entity.Category.class)
                    .addAnnotatedClass(entity.Customer.class)
                    .addAnnotatedClass(entity.Bill.class)
                    .addAnnotatedClass(entity.Invoice.class)
                    .addAnnotatedClass(entity.InvoiceProduct.class)
                    .addAnnotatedClass(entity.Request.class)
                    .addAnnotatedClass(entity.RequestProduct.class)
                    .addAnnotatedClass(entity.Employee.class)
                    .addAnnotatedClass(entity.User.class)
                    .configure().buildSessionFactory();
            session = sessionFactory.openSession();
        } catch (Exception ex) {
            
            System.out.println(ex.toString());
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession()
            throws HibernateException {        
        return session;
    }
    
    public static SessionFactory getSessionFactory()
            throws HibernateException {
        return sessionFactory;
    }
}