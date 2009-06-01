/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package view.panel;

/**
 *
 * @author е
 */
public interface IEntityView<T> {

    /**
     * @return the Editable
     */
    boolean isEditable();

    /**
     * @param Editable the Editable to set
     */
    void setEditable(boolean Editable);

    /**
     * @return the data
     */
    T getData();

    /**
     * @param data the data to set
     */
    void setData(T data);

    /**
     * Очистить data
     */
    void clearData();

    /**
     * Обновить отображение на экране (например, после изменения data)
     */
    void updateDisplay();

}
