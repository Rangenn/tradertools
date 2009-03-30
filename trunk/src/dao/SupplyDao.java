/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import entity.Customer;
import entity.Product;
import entity.Supply;
import entity.SupplyPK;
import entity.Category;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Transaction;
import util.HibUtil;

/**
 *
 * @author е
 */
public class SupplyDao  extends GenericTitledDao<Supply, SupplyPK> {

    //private static ProductDao  productDao = DaoFactory.getProductDao();
    //private static CustomerDao custDao = DaoFactory.getCustomerDao();

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

    public Supply create(SupplyPK pk, String title_alias, int amount_min, int amount_left,
            BigDecimal prev_price, BigDecimal actual_price) {
        Supply res;
        res = (Supply) this.read(pk);
        if (res == null)
            res = new Supply();

        res.setTitleAlias(title_alias);
        res.setAmountMin(amount_min);
        res.setAmountLeft(amount_left);
        res.setSupplyPK(pk);
        res.setProduct((Product) DaoFactory.getProductDao().read(pk.getProductId()));
        res.setCustomer((Customer) DaoFactory.getCustomerDao().read(pk.getSellerId()));
        res.setPrevPrice(prev_price);
        res.setActualPrice(actual_price);
        this.create(res);

        return res;
    }

    public Supply create(String customertitle, String title, String article, String category, String title_alias, int amount_min, int amount_left,
            String prev_price, String actual_price) throws DaoException{
        SupplyPK pk = createPK(customertitle, title, article, category);

        BigDecimal p = (prev_price == null || prev_price.equals("")) ? null : new BigDecimal(prev_price);
        BigDecimal a = (actual_price == null || actual_price.equals("")) ? null : new BigDecimal(actual_price);

        return create(pk, title_alias, amount_min, amount_left, p, a);
    }

    public Supply create(String customertitle, String title, String article, Category category, int amount_min, BigDecimal actual_price) {
        return create(createPK(customertitle, title, article, category.getTitle()), null, amount_min, 0,
            null, actual_price);
    }

    public Supply create(String customertitle, String title, String article, String category, int amount_min,
            String actual_price) throws DaoException{
        return create(customertitle, title, article, category, null, amount_min, 0, null, actual_price);
    }

    private SupplyPK createPK(String customertitle, String title, String article, String category) {
        Customer cust = DaoFactory.getCustomerDao().get(customertitle);
        //if (cust == null)
        //    throw new DaoException(PropsUtil.getProperty("DaoException.ObjectExists"));
        Product pr = null;
        try {
                pr = DaoFactory.getProductDao().create(title, article, category);
        }
        catch (DaoException ex){
            Logger.getLogger(SupplyDao.class.getName()).log(Level.WARNING, ex.getMessage());
            pr = DaoFactory.getProductDao().get(title);
        }
        return new SupplyPK(pr.getId(), cust.getId());
    }

   public void update(Supply s,  String title_alias, int amount_min, int amount_left,
            BigDecimal prev_price, BigDecimal actual_price){
        s.setTitleAlias(title_alias);
        s.setAmountMin(amount_min);
        s.setAmountLeft(amount_left);
        s.setPrevPrice(prev_price);
        s.setActualPrice(actual_price);
        this.update(s);
    }

    public void update(Supply s, String title, String article, Category category, String title_alias, int amount_min, int amount_left,
             BigDecimal prev_price, BigDecimal actual_price){
        s.getProduct().setTitle(title);
        s.getProduct().setArticle(article);
        s.getProduct().setCategory(category);

        this.update(s, title_alias, amount_min, amount_left, prev_price, actual_price);
    }

    public void update(Supply s, String title, String article, int amountleft, BigDecimal actualprice, Category category) {
        this.update(s, null, s.getAmountMin(), amountleft, s.getPrevPrice(), actualprice);
    }
}
