/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import entity.Customer;

/**
 *
 * @author е
 */
public class CustomerDao extends GenericTitledDao<Customer, Integer> {

    CustomerDao(){
        super(Customer.class);
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
