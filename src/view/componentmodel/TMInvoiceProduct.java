/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package view.componentmodel;

import entity.InvoiceProduct;
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
public class TMInvoiceProduct extends AbstractTableModel {

    protected static final int COLUMN_COUNT = 5;

    private List<InvoiceProduct> data;
    protected List<Integer> ColumnsPrefSize;

    public TMInvoiceProduct(List<InvoiceProduct> list) {
        data = list;
        ColumnsPrefSize = new ArrayList<Integer>();
        ColumnsPrefSize.add(new Integer(45));
        ColumnsPrefSize.add(new Integer(260));
//        ColumnsPrefSize.add(new Integer(110));
        for(int i = 2; i < COLUMN_COUNT; i++){
            ColumnsPrefSize.add(new Integer(75));
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
            InvoiceProduct buf = data.get(rowIndex);
            switch (columnIndex)
            {
                case 0: { return rowIndex + 1; }
                case 1: { return buf.getProduct().getTitle(); }
                case 2: { return buf.getPrice(); }
                case 3: { return buf.getAmount(); }
                case 4: { return buf.getCost(); }
                default: { return null; }
            }
        }
        catch (NullPointerException ex){
//            this.fireTableDataChanged(); //хммм
            return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column)
        {
            case 0: { return PropsUtil.getProperty("Index"); }
            case 1: { return PropsUtil.getProperty("Product.Title"); }
            case 2: { return PropsUtil.getProperty("InvoiceProduct.Price"); }
            case 3: { return PropsUtil.getProperty("InvoiceProduct.Amount"); }
            case 4: { return PropsUtil.getProperty("InvoiceProduct.Cost"); }
        }
        return "?";
    }

}
