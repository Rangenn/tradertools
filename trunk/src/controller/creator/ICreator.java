/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller.creator;

import view.form.IFormCreator;

/**
 *
 * @author е
 */
public interface ICreator<T> {

    /**
     * @return the FormCreator
     */
    public IFormCreator<T> getForm();
}
