/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import controller.creator.BillCreator;
import controller.creator.InvoiceCreator;
import controller.creator.CustomerCreator;
import dao.DaoFactory;
import entity.Customer;
import entity.Invoice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import net.sf.jasperreports.engine.JRException;
import org.apache.log4j.Logger;
import org.hibernate.exception.JDBCConnectionException;
import util.HibUtil;
import util.ReportUtil;
import view.FormBillViewer;
import view.FormCustomerManager;
import view.FormInvoiceViewer;

/**
 *
 * @author е
 */
public class CustomerManagingTool {

    public static final int MODE_SUPPLIERS = 1;
    public static final int MODE_CLIENTS = 2;
    public static final int MODE_RECEIVE = 3;
    public static final int MODE_SEND = 4;
    public static final int MODE_BUY = 5;
    public static final int MODE_SELL = 6;

    FormCustomerManager FCustomers;
    List<Customer> ListCustomers;
    Customer MySelf;
    final int mode;

    public CustomerManagingTool(final int mode) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch (Exception ex){
            System.out.println(ex.toString());
        }
        this.mode = mode;

        if (mode == MODE_SUPPLIERS) //ВАЖНО! форма не отслеживает, что за список ей передают
            ListCustomers = DaoFactory.getCustomerDao().getList(true);
        else ListCustomers = DaoFactory.getCustomerDao().getList(false);

        MySelf = (Customer) DaoFactory.getCustomerDao().getMySelf();
        //TODO: выбор mode = suppliers//clients
        FCustomers = new FormCustomerManager(MySelf, ListCustomers, mode);

        ActionListener l;
        
        if (mode == MODE_CLIENTS) {
            l = new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    InvoiceCreator c = new InvoiceCreator(MySelf, ListCustomers,
                            FCustomers.getSelectedCustomer(), mode);
                    c.getForm().getJPanelOkCancel().addOkActionListener(new ActionListener() {

                        public void actionPerformed(ActionEvent e) {
                            FCustomers.updateDisplay();
                        }
                    });
                }
            };
            FCustomers.addjButtonNewInvoiceActionListener(l); //новый счет, MODE_CLIENTS
        }
        
        l = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                int m;
                if (mode == MODE_CLIENTS) m = MODE_RECEIVE;
                else m = MODE_SEND;
                BillCreator c = new BillCreator(MySelf, ListCustomers,
                        FCustomers.getSelectedCustomer(), m);
                c.getForm().getJPanelOkCancel().addOkActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        FCustomers.updateDisplay();
                    }
                });
            }
        };
        FCustomers.addjButtonNewBillActionListener(l); //новый платеж

        l = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                final Invoice i = FCustomers.getSelectedInvoice();
                final FormInvoiceViewer FIV = new FormInvoiceViewer(i);
                FIV.addJMenuItemExportActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        try {
                            HashMap params = new HashMap();
                            params.put("invoice_id", i.getId());
                            String reportName = "Счет № " + i.getId() + " от " +
                                        DateFormat.getDateInstance(DateFormat.DATE_FIELD)
                                            .format(i.getCreationDate()) + ".pdf";
                            ReportUtil.makeReport("src/reports/InvoiceReport.jrxml",
                                    params,
                                    HibUtil.getSession().connection(),
                                    reportName
                                    );
                            JOptionPane.showMessageDialog(FIV, "Exported to \"" + reportName + '"', "Success", JOptionPane.INFORMATION_MESSAGE);

                        } catch (JRException ex) {
                            Logger.getLogger(this.getClass()).error(ex);
                            JOptionPane.showMessageDialog(FIV, "Export failed.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
                FIV.setVisible(true);
            }
        };
        FCustomers.addDoubleClickOnjListInvoicesListener(l); //просмотр счета

        l = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                FormBillViewer FBV = new FormBillViewer(FCustomers.getSelectedBill());
                FBV.setVisible(true);
            }
        };
        FCustomers.addDoubleClickOnjListBillsListener(l); //просмотр платежа

        l = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                final CustomerCreator c = new CustomerCreator(mode);
                c.getForm().getJPanelOkCancel().addOkActionListener(new ActionListener() {

                    public void actionPerformed(ActionEvent e) {
                        //TODO: fix jListCustomers update!
                        ListCustomers.add(c.getData());
                        c.getForm().dispose();
                        //FCustomers.setListCustomers(ListCustomers);
                        FCustomers.updateDisplay();
                    }
                });
            }
        };
        FCustomers.getJPanelAddEditRemove().addjButtonAddActionListener(l); //новый customer

        l = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                Customer c = FCustomers.getSelectedCustomer();
                if (JOptionPane.showConfirmDialog(FCustomers, "Вы уверены, что хотите удалить " + c.getTitle()
                        + "?\n", "Удаление", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
                DaoFactory.getCustomerDao().delete(c);
                ListCustomers.remove(c);
                //FCustomers.get
                FCustomers.updateDisplay();
            }
        };
        FCustomers.getJPanelAddEditRemove().addjButtonRemoveActionListener(l); //удалить customer
        
        FCustomers.updateDisplay();
        FCustomers.setVisible(true);
    }
    
    public static void main(String[] args) {
        try {
           new CustomerManagingTool(MODE_CLIENTS);
//           new CustomerManagingTool(MODE_SUPPLIERS);
        } catch (JDBCConnectionException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

}
