/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import entity.Category;
import entity.Product;
import util.PropsUtil;

/**
 *
 * @author е
 */
public class ProductDao extends GenericTitledDao<Product, Integer> {

    ProductDao(){
        super(Product.class);
    }

    public Product create(String title) throws DaoException{
        if (badString(title)) return null;
        if (exists(title))
            throw new DaoException(PropsUtil.getProperty("DaoException.ObjectExists"));
        Product pr = new Product(title);        
        create(pr);
        return pr;
    }

    //will be deprecated
    public Product create(String title, String article, Category category) throws DaoException{
        Product pr = create(title);
        pr.setArticle(article);
        pr.setCategory(category);
        update(pr);
        return pr;
    }

    @Override
    public Integer create(Product pr) {
        if (pr.getUnit() == null)
                pr.setUnit(DaoFactory.getUnitDao().getDefaultUnit());//?!
        return super.create(pr);
    }

//    public void update(Product pr, String title) {
//        if (badString(title)) return;
//        pr.setTitle(title);
//        update(pr);
//    }
//
//    //will be deprecated
//    public void update(Product pr, String article, Category category) {
//        pr.setArticle(article);
//        pr.setCategory(category);
//        update(pr);
//    }
}
