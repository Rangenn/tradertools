/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import entity.Category;
import util.PropsUtil;

/**
 *
 * @author е
 */
public class CategoryDao extends GenericTitledDao<Category, Integer> {

    CategoryDao(){
        super(Category.class);
    }

    public Category create(String title){
        if (badString(title)) return null;
        Category c = this.get(title);
        if (c == null){
            c = new Category(title);
            create(c);
        }
        return c;
    }

    public void update(Category c, String title) throws DaoException{
        if (badString(title)) return;
        if (exists(title))
            throw new DaoException(PropsUtil.getProperty("DaoException.ObjectExists"));        
        c.setTitle(title);
        //try
        this.update(c);
    }
}
