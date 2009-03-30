/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

/**
 *
 * @author е
 */
public class DaoFactory {

    private static CategoryDao category;
    private static ProductDao product;
    private static SupplyDao supply;
    private static CustomerDao customer;
    //private static CategoryDao category;

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
    
//    public static CategoryDao getCategoryDao() {
//        if (category == null)
//            category = new CategoryDao();
//        return category;
//    }
}
