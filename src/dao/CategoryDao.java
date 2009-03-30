/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import entity.Category;

/**
 *
 * @author е
 */
public class CategoryDao extends GenericTitledDao<Category, Integer> {

    CategoryDao(){
        super(Category.class);
    }

    public Category create(String title){
        if (title == null || title.equals("")) return null;
        Category c = this.get(title);
        if (c == null){
            c = new Category();
            c.setTitle(title);
            create(c);
        }
        return c;
    }

    public void update(Category c, String title){
        if (title == null || title.equals("")) return;
        c.setTitle(title);
        this.update(c);
    }
}
