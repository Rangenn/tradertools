/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import entity.Supply;
import entity.SupplyChange;
import java.math.BigDecimal;

/**
 *
 * @author е
 */
public class SupplyChangeDao  extends GenericDaoHib<SupplyChange, Integer> {

    SupplyChangeDao(){
        super(SupplyChange.class);
    }

    /**
     * При успешном завершении обновляет переданный в качестве параметра supply
     * @param s
     * @param amount_left
     * @param price
     * @return
     */
    public SupplyChange create(Supply s, int amount_left, BigDecimal price) {
        assert s != null;
        assert price != null;
        assert price.doubleValue() > 0;
        
        SupplyChange res = new SupplyChange(amount_left, price);
        res.setSupply(s);
        create(res);
        DaoFactory.getSupplyDao().refresh(s);
        return res;
    }
}
