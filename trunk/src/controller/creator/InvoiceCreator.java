/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller.creator;

import dao.DaoFactory;
import entity.Customer;
import entity.Invoice;
import entity.InvoiceProduct;
import entity.Supply;
import entity.SupplyPK;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;
import javax.swing.JOptionPane;
import view.form.FormInvoiceCreator;

/**
 *
 * @author е
 */
public class InvoiceCreator implements ICreator<Invoice> {

    protected FormInvoiceCreator FInvoiceCreator;

//    private final int mode;

    public InvoiceCreator(Customer myself, List<Customer> ListCustomers, Customer selected, int mode) {
//        this.mode = mode;
        FInvoiceCreator = new FormInvoiceCreator(myself, ListCustomers, mode);
        try {
            FInvoiceCreator.setSelectedCustomer(selected);
        }
        catch (IndexOutOfBoundsException ex) { }
        ActionListener l;
        l = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Invoice data = getForm().getData();
                if (data.getInvoiceProductCollection().isEmpty()) {
                    JOptionPane.showMessageDialog(FInvoiceCreator, "Невозможно создать пустой счет.");
                    return;
                }
                //TODO: вместо заглушки авторизацию
                data.setEmployee(DaoFactory.getEmployeeDao().read(1));//заглушка                
                DaoFactory.getInvoiceDao().create(data);
                for(InvoiceProduct buf : data.getInvoiceProductCollection()) {
                    Supply s = DaoFactory.getSupplyDao().read(
                            new SupplyPK(buf.getProduct().getId(), data.getSellerId().getId()));
//                    s.setAmountLeft(s.getAmountLeft() - buf.getAmount());
                    DaoFactory.getSupplyChangeDao().create(s, s.getAmountLeft() - buf.getAmount(), s.getPrice());
                    //DaoFactory.getSupplyDao().refresh(s);//обновляем остаток товара на складе
                }
                getForm().dispose();
            }
        };
        FInvoiceCreator.getJPanelOkCancel().addOkActionListener(l); //ок: запись сущности в базу

        l = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                getForm().dispose();
            }
        };     
        FInvoiceCreator.getJPanelOkCancel().addCancelActionListener(l); //отмена



        l = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (!FInvoiceCreator.checkSelectedCustomer()) return;
                Invoice data = getForm().getData();
                for(Supply s : getForm().getJPanelSupplyList().getSelectedItems()) {
                    InvoiceProduct i = new InvoiceProduct();
                    i.setProduct(s.getProduct());
                    i.setPrice(s.getPrice());
                    i.setInvoice(getForm().getData());
                    i.setAmount(1);
    //                i.setInvoice(FInvoiceCreator.getData());//?
                    if (!Invoice.сontainsProduct(data, i))
                        data.getInvoiceProductCollection().add(i);
                }
                data.setInvoiceSum(Invoice.calcSum(data));
                getForm().getPanel().updateDisplay();
                //FInvoiceCreator.getPanel().getTable().getModel().fire
                
            }
        };
        FInvoiceCreator.addjButtonAddInvoiceProductActionListener(l); //добавление товара в счет
        l = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (!FInvoiceCreator.checkSelectedCustomer()) return;
                Invoice data = getForm().getData();
                for(InvoiceProduct i : getForm().getPanel().getSelectedItems())
                    data.getInvoiceProductCollection().remove(i);
                data.setInvoiceSum(Invoice.calcSum(data));
                getForm().getPanel().updateDisplay();
            }
        };
        FInvoiceCreator.addjButtonRemoveInvoiceProductActionListener(l); //удаление товара из счета

        l = new ActionListener() {
           
            public void actionPerformed(ActionEvent e) {
                if (!FInvoiceCreator.checkSelectedCustomer()) return;
                InvoiceProduct i = getForm().getSelectedInvoiceProduct();
                String s = JOptionPane.showInputDialog(getForm(),
                        "Введите кол-во товара \"" + i.toString() +'"',
                        i.getAmount());
                int amount = i.getAmount();
                try {
                    amount = Integer.valueOf(s);
                    i.setAmount(amount);
                    i.setCost(i.getPrice().multiply(new BigDecimal(amount)));
                    getForm().getData().setInvoiceSum(Invoice.calcSum(getForm().getData()));
                    getForm().getPanel().updateDisplay();
                }
                catch (NumberFormatException ex) {
                }
            }
        };
        FInvoiceCreator.getPanel().addDoubleClickOnTableListener(l); //изменение кол-ва товара

        FInvoiceCreator.setVisible(true);
    }

    /**
     * @return the FInvoiceCreator
     */
    public FormInvoiceCreator getForm() {
        return FInvoiceCreator;
    }
}
