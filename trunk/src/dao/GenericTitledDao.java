/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import util.HibUtil;

/**
 *
 * @author е
 */
public class GenericTitledDao<T, PK extends Serializable> extends GenericDaoHib{

    public GenericTitledDao(Class<T> type) {
        super(type);
    }

    public List<T> getList(String titleLike){
        Transaction t = HibUtil.getSession().beginTransaction();
        List<T> res =  HibUtil.getSession().createCriteria(type)
                .add(Restrictions.like("title", titleLike))
                .addOrder(Order.asc("title"))
                .list();
        t.commit();
        return res;
    }

    @Override
    public List<T> getList(){
        Transaction t = HibUtil.getSession().beginTransaction();
        List<T> res = HibUtil.getSession().createCriteria(type)
                .addOrder(Order.asc("title"))
                .list();
        t.commit();
        return res;
     }
    
    public T get(String titleLike) {
        T res = null;
        try {
            res = this.getList(titleLike).get(0);
        }
        catch (IndexOutOfBoundsException ex){
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "get(title) has no result");
        }
        return res;
    }
}
