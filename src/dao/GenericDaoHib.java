/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Transaction;
import util.HibUtil;
/**
 *
 * @author е
 */
public class GenericDaoHib <T, PK extends Serializable>
    implements IGenericDao<T, PK> {
    protected final Class<T> type;

    public GenericDaoHib(Class<T> type) {
        this.type = type;
    }

    public PK create(T o) {
        Transaction t = HibUtil.getSession().beginTransaction();
        PK res = (PK) HibUtil.getSession().save(o);
        t.commit();
        return res;
    }

    public T read(PK id) {
        return (T) HibUtil.getSession().get(type, id);
    }

    public void update(T o) {
        Transaction t = HibUtil.getSession().beginTransaction();
        HibUtil.getSession().update(o);
        t.commit();
    }

    public void delete(T o) {
        Transaction t = HibUtil.getSession().beginTransaction();
        HibUtil.getSession().delete(o);
        t.commit();
    }

    public boolean exists(PK id) {
        return (read(id) != null);
    }
//    public void refresh(T o){
//        HibUtil.getSession().refresh(o, LockMode.FORCE);
//    }

    public List<T> getList(){
//        Transaction t = HibUtil.getSession().beginTransaction();
        List<T> res = HibUtil.getSession().createCriteria(type).list();
//        t.commit();
        return res;
    }

    /**
     * Проверяет строку
     */
    public static boolean badString(String str) {
        return (str == null || str.equals(""));
    }
}

