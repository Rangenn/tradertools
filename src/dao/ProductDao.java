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

    public Product create(String title, String article, Category category) throws DaoException{
        if (title == null || title.equals("")) return null;
        if (this.get(title) != null)
            throw new DaoException(PropsUtil.getProperty("DaoException.ObjectExists"));
        Product pr = new Product();
        pr.setTitle(title);
        pr.setArticle(article);
        pr.setCategory(category);
        create(pr);
        return pr;
    }
    
    public Product create(String title, String article, String category) throws DaoException{
        Category c = DaoFactory.getCategoryDao().create(category);
        return create(title, article, c);
    }
}
