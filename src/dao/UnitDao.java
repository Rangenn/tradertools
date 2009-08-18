/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import entity.Unit;
import util.PropsUtil;

/**
 *
 * @author е
 */
public class UnitDao extends GenericTitledDao<Unit, Integer> {

    public static final int DEFAULT_UNIT_ID = 0;

        UnitDao(){
        super(Unit.class);
    }

    public Unit getDefaultUnit() {
        return (Unit) read(DEFAULT_UNIT_ID);
    }

    public Unit create(String title){
        if (badString(title)) return null;
        Unit u = this.get(title);
        if (u == null){
            u = new Unit(title);
            create(u);
        }
        return u;
    }

    public void update(Unit u, String title) throws DaoException{
        if (badString(title)) return;
        if (exists(title))
            throw new DaoException(PropsUtil.getProperty("DaoException.ObjectExists"));
        u.setTitle(title);
        //try
        this.update(u);
    }
}
