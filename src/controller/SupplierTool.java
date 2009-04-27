/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import dao.DaoFactory;
import entity.Customer;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import org.hibernate.exception.JDBCConnectionException;
import view.FormCustomerManager;

/**
 *
 * @author е
 */
public class SupplierTool {


    FormCustomerManager FCustomers;
    List<Customer> ListCustomers;
    Customer MySelf;

    public SupplierTool() {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch (Exception ex){
            System.out.println(ex.toString());
        }

        ListCustomers = DaoFactory.getCustomerDao().getList();
        MySelf = (Customer) DaoFactory.getCustomerDao().read(0);
        FCustomers = new FormCustomerManager(MySelf, ListCustomers, true);

        FCustomers.setVisible(true);
    }
    
    public static void main(String[] args) {
        try {
           new SupplierTool();
        } catch (JDBCConnectionException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

}
