/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package view.componentmodels;

import entity.Category;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 *
 * @author е
 */
public class CMCategory extends AbstractListModel implements ComboBoxModel {

    private List<Category> data;
    private Category SelectedItem;

    public CMCategory(List<Category> list) {
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
            Category buf = (Category)anItem;
            for (Category m : data)
                if (buf.getId() == m.getId()){
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
