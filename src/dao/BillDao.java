/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import entity.Bill;
import entity.Customer;
import java.util.List;
import org.hibernate.Query;
import util.HibUtil;

/**
 *
 * @author е
 */
public class BillDao extends GenericDaoHib<Bill, Integer>{

    public BillDao() {
        super(Bill.class);
    }

    public List<Bill> getList(Customer receiver, Customer sender) {
        Query q = HibUtil.getSession().getNamedQuery("Bill.findByReceiverIdSenderId");
        q.setInteger("SenderId", sender.getId());
        q.setInteger("ReceiverId", receiver.getId());
        return q.list();
    }
}
