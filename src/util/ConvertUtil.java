/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author е
 */
public class ConvertUtil {

    public static MouseListener convert(final ActionListener l) {
        MouseListener buf = new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
               if (e.getClickCount() == 2)
                   l.actionPerformed(null);
            }
        };
       return buf;
    }
}
