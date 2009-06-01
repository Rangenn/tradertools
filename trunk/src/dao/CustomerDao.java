/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import entity.Customer;
import java.util.List;

/**
 *
 * @author е
 */
public class CustomerDao extends GenericTitledDao<Customer, Integer> {

    CustomerDao(){
        super(Customer.class);
    }

    public Customer getMySelf() {
        return (Customer) read(0);
    }

    public List<Customer> getList(boolean isSupplier) {
        List<Customer> res = super.getList();
        res.remove(getMySelf());//удаляем себя из списка
        for(int i = 0; i < res.size(); i++) {
            Customer c = res.get(i);
            if (c.IsSupplier() != isSupplier) {
                res.remove(c);
                i--;
            }
        }

        return res;
    }
//    public Customer create(String title, String ITN, String comment, String Phone, String Email, boolean isSupplier) throws DaoException{
//        Customer res = new Customer();
//
//        res.setTitle(title);
//        res.setITN(ITN);
//        res.setCommentary(comment);
//        res.setPhone(Phone);
//        res.setEmail(Email);
//        res.setIsSupplier(isSupplier);
//
//        this.create(res);
//
//        return res;
//    }
}
