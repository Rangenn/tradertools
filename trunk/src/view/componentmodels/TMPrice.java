/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package view.componentmodels;

import entity.Price;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import util.PropsUtil;

/**
 *
 * @author е
 */
public class TMPrice extends AbstractTableModel{

    protected static final int COLUMN_COUNT = 6;
    private List<Price> data;
    protected List<Integer> ColumnsPrefSize;

    public TMPrice(List<Price> list) {
        data = list;
        ColumnsPrefSize = new ArrayList<Integer>();
        ColumnsPrefSize.add(new Integer(45));
        ColumnsPrefSize.add(new Integer(300));
        ColumnsPrefSize.add(new Integer(110));
        for(int i = 3; i < COLUMN_COUNT; i++){
            ColumnsPrefSize.add(new Integer(100));
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
        Object res = null;
        try{
            switch (columnIndex)
            {
                case 0: { return rowIndex; }
                case 1: { return data.get(rowIndex).getProductId().getTitle(); }
                case 2: { return data.get(rowIndex).getProductId().getArticle(); }
                case 3: {
                    if (data.get(rowIndex).getBuyingPrice() == null)
                        return null;
                    else return data.get(rowIndex).getBuyingPrice().setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
                }
                case 4: { return data.get(rowIndex).getSellingPrice().setScale(0, BigDecimal.ROUND_HALF_UP).intValue(); }
                case 5: { return data.get(rowIndex).getProductId().getCategoryId(); }
            }
        }
        catch (NullPointerException ex){

        }
        return res;
    }

    @Override
    public String getColumnName(int column) {
        switch (column)
        {
            case 0: { return PropsUtil.getProperty("Index"); }
            case 1: { return PropsUtil.getProperty("Price.Title"); }
            case 2: { return PropsUtil.getProperty("Price.Article"); }
            case 3: { return PropsUtil.getProperty("Price.BuyingPrice"); }
            case 4: { return PropsUtil.getProperty("Price.SellingPrice"); }
            case 5: { return PropsUtil.getProperty("Price.Category"); }
        }
        return "?";
    }

}
