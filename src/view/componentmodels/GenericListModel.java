/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package view.componentmodels;

import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 *
 * @author е
 */
public class GenericListModel<T> extends AbstractListModel implements ComboBoxModel {

    private List<T> data;
    private T SelectedItem;

    public GenericListModel(List<T> list) {
        data = list;
    }

    public int getSize() {
        return data.size();
    }

    public Object getElementAt(int index) {
        return data.get(index);
    }

    public void setSelectedItem(Object anItem) {
        if (data.size() != 0 && anItem != null) {
            T buf = (T)anItem;
            for (T m : data)
                if (buf.equals(m)){
                    SelectedItem = m;
                    return;
                }
        }
        SelectedItem = null;
    }

    public Object getSelectedItem() {
        return SelectedItem;
    }

}

