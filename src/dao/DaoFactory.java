/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import entity.Bill;
import entity.Invoice;
import entity.InvoiceProduct;

/**
 *
 * @author е
 * Работа с сущностями: используем только методы dao, получаемые через daofactory.
 * не используем конструкторы сущностей.
 */
public class DaoFactory {

    private static CategoryDao category;
    private static ProductDao product;
    private static SupplyDao supply;
    private static CustomerDao customer;
    private static BillDao bill;
    private static InvoiceDao invoice;
    private static GenericDaoHib<InvoiceProduct, Integer> invoiceProduct;

    public static CategoryDao getCategoryDao() {
        if (category == null)
            category = new CategoryDao();
        return category;
    }
    
    public static ProductDao getProductDao() {
        if (product == null)
            product = new ProductDao();
        return product;
    }
    
    public static SupplyDao getSupplyDao() {
        if (supply == null)
            supply = new SupplyDao();
        return supply;
    }
    
    public static CustomerDao getCustomerDao() {
        if (customer == null)
            customer = new CustomerDao();
        return customer;
    }

    /**
     * @return the bill
     */
    public static BillDao getBillDao() {
        if (bill == null)
            bill = new BillDao();
        return bill;
    }

    /**
     * @return the invoice
     */
    public static InvoiceDao getInvoiceDao() {
        if (invoice == null)
            invoice = new InvoiceDao();
        return invoice;
    }

    /**
     * @return the invoiceProduct
     */
    public static GenericDaoHib<InvoiceProduct, Integer> getInvoiceProductDao() {
        if (invoiceProduct == null)
            invoiceProduct = new GenericDaoHib<InvoiceProduct, Integer>(InvoiceProduct.class);
        return invoiceProduct;
    }
    
//    public static CategoryDao getCategoryDao() {
//        if (category == null)
//            category = new CategoryDao();
//        return category;
//    }
}
