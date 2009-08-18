/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller.creator;

import dao.BillDao;
import dao.DaoFactory;
import dao.GenericDaoHib;
import entity.Bill;
import entity.Customer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.JOptionPane;
import view.form.FormBillCreator;

/**
 *
 * @author е
 */
public class BillCreator implements ICreator<Bill> {

    protected FormBillCreator FBillCreator;

    public BillCreator(Customer myself, List<Customer> ListCustomers, Customer selected, int mode) {
//        this.mode = mode;
        FBillCreator = new FormBillCreator(myself, ListCustomers, mode);
        try {
            FBillCreator.setSelectedCustomer(selected);
        }
        catch (IndexOutOfBoundsException ex) { }
        ActionListener l;

        l = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                
                getForm().getData().setEmployee(DaoFactory.getEmployeeDao().read(1));
                if (getForm().getData().getBillSum().compareTo(new BigDecimal(0)) <= 0) {
                    JOptionPane.showMessageDialog(getForm(),
                            "Сумма платежа должна быть положительной.");
                    return;
                }
                String s = getForm().getData().getPurpose();
                if (s == null || s.equals("")) {
                    JOptionPane.showMessageDialog(getForm(),
                            "Не заполнено поле 'Назначение платежа'.");
                    return;
                }

                DaoFactory.getBillDao().create(
                        getForm().getData());
                getForm().dispose();
            }
        };
        FBillCreator.getJPanelOkCancel().addOkActionListener(l); //запись сущности в базу

        l = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                getForm().dispose();
            }
        };
        FBillCreator.getJPanelOkCancel().addCancelActionListener(l); //отмена

        FBillCreator.setVisible(true);
    }

    /**
     * @return the FBillCreator
     */
    public FormBillCreator getForm() {
        return FBillCreator;
    }
}
