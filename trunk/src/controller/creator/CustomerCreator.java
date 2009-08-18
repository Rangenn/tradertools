/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller.creator;

import controller.CustomerManagingTool;
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
    private Customer data;

    public CustomerCreator(int mode) {
        FCustomerCreator = new FormCustomerCreator(mode);
        if (mode == CustomerManagingTool.MODE_SUPPLIERS) { //?! выносить логику такого типа в креатор или в форму
            FCustomerCreator.getData().setIsSupplier(true);
            FCustomerCreator.setTitle("New Supplier");
        } else {
            FCustomerCreator.setTitle("New Client");
            FCustomerCreator.getData().setIsSupplier(false);
        }
        ActionListener l;
        l = new ActionListener() {

            public void actionPerformed(ActionEvent e) {               
                if (getForm().getData().getTitle() == null || getForm().getData().getTitle().equals("")) {
                    JOptionPane.showMessageDialog(getForm(), "Введите имя/название.");
                    return;
                }
                data = FCustomerCreator.getData();//запоминаем данные из панели формы в креатор
                DaoFactory.getCustomerDao().create(data);
                //getForm().dispose();//удаляем форму
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

    /**
     * @return the data
     */
    public Customer getData() {
        return data;
    }

//    /**
//     * @param data the data to set
//     */
//    public void setData(Customer data) {
//        this.data = data;
//        getForm().getPanel().setData(data);
//    }
}
