/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import entity.Bill;
import entity.Employee;
import entity.Invoice;
import entity.InvoiceProduct;
import entity.RequestProduct;

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
    private static SupplyChangeDao supplyChange;
    private static CustomerDao customer;
    private static UnitDao unit;
    private static BillDao bill;
    private static InvoiceDao invoice;
    private static GenericDaoHib<InvoiceProduct, Integer> invoiceProduct;
    private static RequestDao request;
    private static GenericDaoHib<RequestProduct, Integer> requestProduct;
    private static GenericDaoHib<Employee, Integer> employee;

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

    /**
     * @return the request
     */
    public static RequestDao getRequestDao() {
        if (request == null)
            request = new RequestDao();
        return request;
    }

    /**
     * @return the requestProduct
     */
    public static GenericDaoHib<RequestProduct, Integer> getRequestProductDao() {
        if (requestProduct == null)
            requestProduct = new GenericDaoHib<RequestProduct, Integer>(RequestProduct.class);
        return requestProduct;
    }

    /**
     * @return the employee
     */
    public static GenericDaoHib<Employee, Integer> getEmployeeDao() {
        if (employee == null)
            employee = new GenericDaoHib<Employee, Integer>(Employee.class);
        return employee;
    }

    /**
     * @return the supplyChange
     */
    public static SupplyChangeDao getSupplyChangeDao() {
        if (supplyChange == null)
            supplyChange = new SupplyChangeDao();
        return supplyChange;
    }

    /**
     * @return the unit
     */
    public static UnitDao getUnitDao() {
        if (unit == null)
            unit = new UnitDao();
        return unit;
    }
    
}
