/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import entity.Customer;
import entity.Request;
import java.util.List;
import org.hibernate.Query;
import util.HibUtil;

/**
 *
 * @author е
 */
public class RequestDao extends GenericDaoHib<Request, Integer>{

    public RequestDao() {
        super(Request.class);
    }

     public List<Request> getList(Customer receiver, Customer sender) {
        Query q = HibUtil.getSession().getNamedQuery("Bill.findByReceiverIdSenderId");
        q.setInteger("SenderId", sender.getId());
        q.setInteger("ReceiverId", receiver.getId());
        return q.list();
    }
}
