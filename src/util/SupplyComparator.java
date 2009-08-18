/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import entity.Supply;
import java.util.Comparator;

/**
 *
 * @author е
 */
public class SupplyComparator implements Comparator<Supply>{

    public int compare(Supply o1, Supply o2) throws NullPointerException {
        boolean n1 = (o1.getProduct().getTitle() == null);
        boolean n2 = (o2.getProduct().getTitle() == null);
        if (n1)
            if (n2)
                return 0;
            else
                return -1;
        else
            if (n2)
                return 1;
            else {
                return (o1.getProduct().getTitle().compareToIgnoreCase(o2.getProduct().getTitle()));
            }
    }

}
