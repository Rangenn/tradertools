/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package view.componentmodel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author е
 *
 * ВНИМАНИЕ! класс использует отражения для работы.
 */
public class TableModelGeneric<T> extends AbstractTableModel{

    private Class<T> type;
    private List<T> data;

    public TableModelGeneric(List<T> data, Class<T> type) {
        this.type = type;
        this.data = data;
    }

    public int getRowCount() {
        return data.size();
    }

    public int getColumnCount() {
        Method[] arr = type.getMethods();
        int count = arr.length;
        String str = null;
        for (int i = 0; i < arr.length; i++)
        {
            str = arr[i].getName();
            if (!str.startsWith("get") || str.equals("getClass") || str.equals("getId"))
                count--;
        }
        return count;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex >= 0 && columnIndex < getColumnCount())
        {
            try {
                Method[] arr = type.getMethods();
                int count = 0;
                String str = null;
                for (int i = 0; i < arr.length; i++)
                {
                    str = arr[i].getName();
                    if (!(!str.startsWith("get") || str.equals("getClass") || str.equals("getId")))
                    {
                        if (count == columnIndex)
                            return arr[i].invoke(data.get(rowIndex), new Object());
                        count++;
                    }
                }
            } catch (IllegalArgumentException ex) {
                Logger.getLogger(TableModelGeneric.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                Logger.getLogger(TableModelGeneric.class.getName()).log(Level.SEVERE, null, ex);
            }
            catch (InvocationTargetException ex) {
                Logger.getLogger(TableModelGeneric.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
        else throw new IndexOutOfBoundsException("Некорректный номер столбца");
    }
       
    

    @Override
    public String getColumnName(int column) {
        if (column >= 0 && column < getColumnCount())
        {
            Method[] arr = type.getMethods();
            int count = 0;
            String str = null;
            for (int i = 0; i < arr.length; i++)
            {
                str = arr[i].getName();
                if (!(!str.startsWith("get") || str.equals("getClass") || str.equals("getId")))
                {
                    if (count == column)
                        return str;
                    count++;
                }
            }
        }
        return "?";
    }

    /**
     * @return the data
     */
    public List<T> getData() {
        return data;
    }

}


