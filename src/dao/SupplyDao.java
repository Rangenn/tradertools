/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import entity.Customer;
import entity.Product;
import entity.Supply;
import entity.SupplyPK;
import java.math.BigDecimal;
import java.util.List;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Transaction;
import util.HibUtil;
import util.PropsUtil;

/**
 *
 * @author е
 */
public class SupplyDao extends GenericDaoHib<Supply, SupplyPK> {

    public static final int DEFAULT_ORDER_AMOUNT = 1;
    public static final int DEFAULT_AMOUNT_MIN = 0;

    SupplyDao(){
        super(Supply.class);
    }    

    @Override
    public List<Supply> getList(){
        Transaction t = HibUtil.getSession().beginTransaction();
        List<Supply> res = HibUtil.getSession().createCriteria(Supply.class)//.addOrder(Order.asc("productId"))
                .list();
        t.commit();
        return res;
     }

    public Supply create(SupplyPK pk, BigDecimal actual_price, int amount_left) throws DaoException {
        if (pk == null || actual_price == null) return null;
        Supply res = (Supply) this.read(pk);
        if (res != null)
             throw new DaoException(PropsUtil.getProperty("DaoException.ObjectExists"));
        res = new Supply(pk, 0, actual_price, amount_left);
//        res.setPrice(actual_price);
//        res.setTitleAlias(title_alias);
//        res.setAmountLeft(amount_left);
        res.setProduct((Product) DaoFactory.getProductDao().read(pk.getProductId()));
        res.setCustomer((Customer) DaoFactory.getCustomerDao().read(pk.getSellerId()));
//        res.setPrevPrice(prev_price);
        res.setDefaultOrderAmount(1);
        this.create(res);
        return res;
    }

    @Override
    public SupplyPK create(Supply s) {
        if (s.getDefaultOrderAmount() == null)
            s.setDefaultOrderAmount(DEFAULT_ORDER_AMOUNT);
        if (s.getAmountMin() == null)
            s.setAmountMin(DEFAULT_AMOUNT_MIN);
        return super.create(s);
    }

//    public Supply create(String customertitle, String title,
//            int amount_min, String actual_price) throws DaoException{
//        //Category cat = DaoFactory.getCategoryDao().create(category);
//        SupplyPK pk = createPK(customertitle, title);
//
////        BigDecimal p = (prev_price == null || prev_price.equals("")) ? null :
////            new BigDecimal(prev_price);
//        BigDecimal a = (actual_price == null || actual_price.equals("")) ? null :
//            new BigDecimal(actual_price);
//
//        return create(pk, amount_min, a);
//    }

//    public Supply create(String customertitle, String title, String article, String category,
//           int amount_min, String actual_price) throws DaoException{
//        return create(customertitle, title, article, category, null, amount_min, 0, null, actual_price);
//    }

//    public Supply create(String customertitle, String title,
//            int amount_min, BigDecimal actual_price) throws DaoException {
//        return create(createPK(customertitle, title), amount_min,
//                actual_price);
//    }

    public static SupplyPK createPK(Customer cust, String producttitle) throws DaoException {
//        Customer cust = DaoFactory.getCustomerDao().get(customertitle);
//        if (cust == null)
//            throw new DaoException(PropsUtil.getProperty("DaoException.ObjectNotFound"));
        Product pr = null;
        try {
                pr = DaoFactory.getProductDao().create(producttitle);
        }
        catch (DaoException ex){
            Logger.getLogger(SupplyDao.class.getName()).log(Level.WARN, ex.getMessage());
            pr = DaoFactory.getProductDao().get(producttitle);
        }
        return new SupplyPK(pr.getId(), cust.getId());
    }

   public void update(Supply s,  String title_alias, int amount_min, int amount_left,
            BigDecimal actual_price){
        s.setTitleAlias(title_alias);
        s.setAmountMin(amount_min);
        s.setAmountLeft(amount_left);
        s.setPrice(actual_price);
        this.update(s);
    }

//    public BigDecimal getPrice(SupplyPK id) {
//        Query q = HibUtil.getSession().getNamedQuery("Invoice.findBySellerIdBuyerId");
//        q.setParameter("Id", id);
//        return (BigDecimal) q.uniqueResult();
//    }
}
