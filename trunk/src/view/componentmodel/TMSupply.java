/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package view.componentmodel;

import entity.Supply;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import util.PropsUtil;

/**
 *
 * @author е
 */
public class TMSupply extends AbstractTableModel {

    protected static final int COLUMN_COUNT = 8;
    
    private List<Supply> data;
    protected List<Integer> ColumnsPrefSize;

    public TMSupply(List<Supply> list) {
        data = list;
        ColumnsPrefSize = new ArrayList<Integer>();
        ColumnsPrefSize.add(new Integer(45));
        ColumnsPrefSize.add(new Integer(300));
        ColumnsPrefSize.add(new Integer(110));
        for(int i = 3; i < COLUMN_COUNT; i++){
            ColumnsPrefSize.add(new Integer(90));
        }
        //ColumnsPrefSize.set(0,new Integer(45));
    }

    public int getColumnPreferredWidth(int index) {
        return ColumnsPrefSize.get(index);
    }

    public int getRowCount() {
        return data.size();
    }

    public int getColumnCount() {
        return COLUMN_COUNT;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            Supply buf = data.get(rowIndex);
            switch (columnIndex)
            {
                case 0: { return rowIndex; }
                case 1: { return buf.getProduct().getTitle(); }
                case 2: { return buf.getProduct().getArticle(); }              
                case 3: { return buf.getAmountMin(); }
                case 4: { return buf.getDefaultOrderAmount(); }
                case 5: { return buf.getPrice().setScale(0, BigDecimal.ROUND_HALF_UP).intValue(); }
                case 6: { return buf.getAmountLeft(); }
                case 7: { return buf.getProduct().getCategory(); }
                default: { return null; }
            }
        }
        catch (NullPointerException ex){
            this.fireTableDataChanged(); //хммм
            return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column)
        {
            case 0: { return PropsUtil.getProperty("Index"); }
            case 1: { return PropsUtil.getProperty("Product.Title"); }
            case 2: { return PropsUtil.getProperty("Product.Article"); }
            case 3: { return PropsUtil.getProperty("Supply.AmountMin"); }
            case 4: { return PropsUtil.getProperty("Supply.DefaultOrderAmount"); }
            case 5: { return PropsUtil.getProperty("Supply.ActualPrice"); }
            case 6: { return PropsUtil.getProperty("Supply.AmountLeft"); }
            case 7: { return PropsUtil.getProperty("Product.Category"); }
        }
        return "?";
    }

}
