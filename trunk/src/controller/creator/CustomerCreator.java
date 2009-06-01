/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller.creator;

import dao.DaoFactory;
import entity.Customer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import view.form.FormCustomerCreator;

/**
 *
 * @author е
 */
public class CustomerCreator implements ICreator<Customer> {

    protected FormCustomerCreator FCustomerCreator;

    public CustomerCreator(int mode) {
        FCustomerCreator = new FormCustomerCreator(mode);
        ActionListener l;
        l = new ActionListener() {

            public void actionPerformed(ActionEvent e) {               
                if (getForm().getData().getTitle() == null || getForm().getData().getTitle().equals("")) {
                    JOptionPane.showMessageDialog(getForm(), "Введите имя/название.");
                    return;
                }
                Customer c = FCustomerCreator.getData();
                DaoFactory.getCustomerDao().create(c);
                getForm().dispose();
            }
        };
        FCustomerCreator.getJPanelOkCancel().addOkActionListener(l); //OK

        l = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                getForm().dispose();
            }
        };
        FCustomerCreator.getJPanelOkCancel().addCancelActionListener(l); //отмена

        FCustomerCreator.setVisible(true);
    }

    /**
     * @return the FCustomerCreator
     */
    public FormCustomerCreator getForm() {
        return FCustomerCreator;
    }
}
