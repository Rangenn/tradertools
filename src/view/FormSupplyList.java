/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * FormSupplyList.java
 *
 * Created on 17.02.2009, 23:33:00
 */

package view;

import java.awt.event.ActionEvent;
import entity.Supply;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JOptionPane;
import util.PropsUtil;

/**
 *
 * @author е
 */
public class FormSupplyList extends javax.swing.JFrame {

    /** Creates new form FormSupplyList */
    public FormSupplyList(List<Supply> list) {
        initComponents();
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        jPanelSupplyList1.setItemList(list);     
        
        JCheckBoxMenuItem m = null;
        ActionListener l = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                setVisiblePriceColumns();
            }
        };
        for(int i = 0; i < jPanelSupplyList1.getTable().getColumnCount(); i++){
            m = new JCheckBoxMenuItem(jPanelSupplyList1.getTable().getColumnName(i), true);
            m.addActionListener(l);
            jMenuView.add(m);
        }
        setVisiblePriceColumns();
        loadTextProps();        
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelSupplyList1 = new view.JPanelSupplyList();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuItemSaveAs = new javax.swing.JMenuItem();
        jMenuItemQuit = new javax.swing.JMenuItem();
        jMenuEdit = new javax.swing.JMenu();
        jMenuItemAdd = new javax.swing.JMenuItem();
        jMenuItemEdit = new javax.swing.JMenuItem();
        jMenuItemRemove = new javax.swing.JMenuItem();
        jMenuView = new javax.swing.JMenu();
        jMenuHelp = new javax.swing.JMenu();
        jMenuItemAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Trader Tools");

        jMenuFile.setText("Файл");

        jMenuItemSaveAs.setText("Сохранить как...");
        jMenuFile.add(jMenuItemSaveAs);

        jMenuItemQuit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemQuit.setText("Выход");
        jMenuItemQuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemQuitActionPerformed(evt);
            }
        });
        jMenuFile.add(jMenuItemQuit);

        jMenuBar1.add(jMenuFile);

        jMenuEdit.setText("Правка");

        jMenuItemAdd.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_EQUALS, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemAdd.setText("Add");
        jMenuEdit.add(jMenuItemAdd);

        jMenuItemEdit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ENTER, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemEdit.setText("Edit");
        jMenuEdit.add(jMenuItemEdit);

        jMenuItemRemove.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_DELETE, 0));
        jMenuItemRemove.setText("Remove");
        jMenuEdit.add(jMenuItemRemove);

        jMenuBar1.add(jMenuEdit);

        jMenuView.setText("View");
        jMenuBar1.add(jMenuView);

        jMenuHelp.setText("Help");

        jMenuItemAbout.setText("About");
        jMenuItemAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemAboutActionPerformed(evt);
            }
        });
        jMenuHelp.add(jMenuItemAbout);

        jMenuBar1.add(jMenuHelp);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelSupplyList1, javax.swing.GroupLayout.DEFAULT_SIZE, 754, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelSupplyList1, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItemQuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemQuitActionPerformed
        this.dispose();
    }//GEN-LAST:event_jMenuItemQuitActionPerformed

    private void jMenuItemAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemAboutActionPerformed
        JOptionPane.showMessageDialog(this, PropsUtil.getProperty("FormSupplyList.About.text"),
                PropsUtil.getProperty("jMenuItemAbout.text"), JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_jMenuItemAboutActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuEdit;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenu jMenuHelp;
    private javax.swing.JMenuItem jMenuItemAbout;
    private javax.swing.JMenuItem jMenuItemAdd;
    private javax.swing.JMenuItem jMenuItemEdit;
    private javax.swing.JMenuItem jMenuItemQuit;
    private javax.swing.JMenuItem jMenuItemRemove;
    private javax.swing.JMenuItem jMenuItemSaveAs;
    private javax.swing.JMenu jMenuView;
    private view.JPanelSupplyList jPanelSupplyList1;
    // End of variables declaration//GEN-END:variables

    // <editor-fold defaultstate="collapsed" desc="ActionListeners">
    public void addjMenuItemAddActionListener(ActionListener l)
    {
        jMenuItemAdd.addActionListener(l);
    }
    public void removejMenuItemAddActionListener(ActionListener l)
    {
        jMenuItemAdd.removeActionListener(l);
    }

    public void addjMenuItemEditActionListener(final ActionListener l)
    {
        jPanelSupplyList1.addDoubleClickOnTableListener(l);
        jMenuItemEdit.addActionListener(l);
    }
    
    public void removejMenuItemEditActionListener(ActionListener l)
    {
        jMenuItemEdit.removeActionListener(l);
    }

    public void addjMenuItemDeleteActionListener(ActionListener l)
    {
        jMenuItemRemove.addActionListener(l);
    }
    public void removejMenuItemDeleteActionListener(ActionListener l)
    {
        jMenuItemRemove.removeActionListener(l);
    }

    // </editor-fold> 
    /**
     * @return the jPanelSupplyList1.getJPanelSearch()
     */
//    public view.JPanelSearch getJPanelSearch() {
//        return jPanelSupplyList1.getJPanelSearch();
//    }

    public view.JPanelSupplyList getJPanelSupplyList() {
        return jPanelSupplyList1;
    }

    public Supply getSelectedPrice(){
        return jPanelSupplyList1.getSelectedItem();
    }

    public int getSelectedPriceIndex(){
        return jPanelSupplyList1.getSelectedIndex();
    }

    public void setSelectedPrice(Supply p){
        jPanelSupplyList1.setSelectedItem(p);
    }

    public void setSelectedPrice(int index){
        jPanelSupplyList1.setSelectedIndex(index);
    }

    public void setVisiblePriceColumns(){
        for(int i = 0; i < jPanelSupplyList1.getTable().getColumnCount(); i++){
            jPanelSupplyList1.setColumnVisible(i,((JCheckBoxMenuItem)jMenuView.getItem(i)).getState());
        }
    }

    private void loadTextProps() {
        try {
            this.setIconImage(ImageIO.read(new File(PropsUtil.getProperty("icon.hammer"))));
        } catch (IOException ex) {
            Logger.getLogger(FormSupplyList.class.getName()).log(Level.SEVERE, null, ex);
        }
        jMenuItemAdd.setIcon(new ImageIcon(
                getClass().getResource(PropsUtil.getProperty("icon.add"))));
        jMenuItemEdit.setIcon(new ImageIcon(
                getClass().getResource(PropsUtil.getProperty("icon.edit"))));
        jMenuItemRemove.setIcon(new ImageIcon(
                getClass().getResource(PropsUtil.getProperty("icon.remove"))));
        jMenuItemAbout.setIcon(new ImageIcon(
                getClass().getResource(PropsUtil.getProperty("icon.info"))));
        setTitle(PropsUtil.getProperty("FormSupplyList.title"));
        jMenuFile.setText(PropsUtil.getProperty("jMenuFile.text"));
        jMenuItemSaveAs.setText(PropsUtil.getProperty("jMenuItemSaveAs.text"));
        jMenuItemQuit.setText(PropsUtil.getProperty("jMenuItemQuit.text"));
        jMenuEdit.setText(PropsUtil.getProperty("jMenuEdit.text"));
        jMenuView.setText(PropsUtil.getProperty("jMenuView.text"));
        jMenuHelp.setText(PropsUtil.getProperty("jMenuHelp.text"));
        jMenuItemAbout.setText(PropsUtil.getProperty("jMenuItemAbout.text"));
    }
}
