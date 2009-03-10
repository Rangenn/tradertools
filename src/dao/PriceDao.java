/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import entity.Category;
import entity.Price;
import entity.Product;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import util.HibUtil;
import util.PropsUtil;

/**
 *
 * @author е
 */
public class PriceDao extends GenericTitledDao<Price, Integer> {

    public PriceDao(){
        super(Price.class);
    }

    @Override
    public List<Price> getList(){
        Transaction t = HibUtil.getSession().beginTransaction();
        List<Price> res = HibUtil.getSession().createCriteria(Price.class)//.addOrder(Order.asc("productId"))
                .list();
        t.commit();
        return res;
     }

    public Price create(BigDecimal buyingprice, BigDecimal sellingprice, Product pr) throws DaoException{
        if (pr == null) return null;
        if (pr.getPriceCollection() != null && !pr.getPriceCollection().isEmpty())
            throw new DaoException(PropsUtil.getProperty("DaoException.ObjectExists"));
        Price p = new Price();
        p.setProductId(pr);
        p.setBuyingPrice(buyingprice);
        p.setSellingPrice(sellingprice);
        this.create(p);
        return p;
    }

    public Price create(String title, String article, BigDecimal buyingprice, BigDecimal sellingprice, Category category) throws DaoException{
        Product pr = null;
        try {
                pr = new ProductDao().create(title, article, category);
        }
        catch (DaoException ex){
            Logger.getLogger(PriceDao.class.getName()).log(Level.WARNING, ex.getMessage());
            pr = new ProductDao().get(title);
        }
        return create(buyingprice, sellingprice, pr);
    }

    public Price create(String title, String article, String buyingprice, String sellingprice, String category) throws DaoException{
        Category c = new CategoryDao().create(category);
        BigDecimal b = (buyingprice == null || buyingprice.equals("")) ? null : new BigDecimal(buyingprice);
        BigDecimal s = (sellingprice == null || sellingprice.equals("")) ? null : new BigDecimal(sellingprice);
        return create(title, article, b, s, c);
    }

    public void update(Price p, String title, String article, BigDecimal buyingprice, BigDecimal sellingprice, Category category){
        p.getProductId().setTitle(title);
        p.getProductId().setArticle(article);
        p.setBuyingPrice(buyingprice);
        p.setSellingPrice(sellingprice);
        p.getProductId().setCategoryId(category);
        this.update(p);
    }
}
