/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import entity.Customer;
import entity.Invoice;
import java.util.List;
import org.hibernate.Query;
import util.HibUtil;

/**
 *
 * @author е
 */
public class InvoiceDao extends GenericDaoHib<Invoice, Integer>{

    public InvoiceDao() {
        super(Invoice.class);
    }

    public List<Invoice> getList(Customer seller, Customer buyer) {
        Query q = HibUtil.getSession().getNamedQuery("Invoice.findBySellerIdBuyerId");
        q.setInteger("SellerId", seller.getId());
        q.setInteger("BuyerId", buyer.getId());
        return q.list();
    }
}
