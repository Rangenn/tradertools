/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package view.form;

import view.JPanelOkCancel;
import view.panel.IEntityView;

/**
 * @param T entity's type
 * @author е
 */
public interface IFormCreator<T> {

    /**
     * панель с кнопками результата диалога
     * @return
     */
    public JPanelOkCancel getJPanelOkCancel();

    /**
     * Панель формы, содержащая и отображающая данные о сущности entity типа T
     * @return
     */
    public IEntityView<T> getPanel(); //логичнее название getView

    /**
     * данные о сущности entity типа T (полученные из панели)
     * @return
     */
    public T getData();
}
