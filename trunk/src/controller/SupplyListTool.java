/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import dao.DaoException;
import dao.DaoFactory;
import dao.SupplyDao;
import entity.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import org.hibernate.exception.JDBCConnectionException;
import util.PropsUtil;
import view.*;
import view.componentmodels.GenericListModel;
/**
 *
 * @author е
 */
public class SupplyListTool {

    FormSupplyList FSupplyList;
    List<Supply> ListSupplies;

    public SupplyListTool(){
        try{
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch (Exception ex){
            System.out.println(ex.toString());
        }        

        ListSupplies = DaoFactory.getSupplyDao().getList();

        FSupplyList = new FormSupplyList(ListSupplies);
        JPanelAddEditRemove aer = FSupplyList.getJPanelSupplyList().getJPanelAddEditRemove();

        ActionListener l = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StartAddSupply();
            }
        };
//        FSupplyList.addjMenuItemAddActionListener(l);
        aer.addjButtonAddActionListener(l);
        l = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StartEditSupply();
            }
        };
//        FSupplyList.addjMenuItemEditActionListener(l);
        aer.addjButtonEditActionListener(l);

        l = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StartDeleteSupply();
            }
        };
//        FSupplyList.addjMenuItemDeleteActionListener(l);
        aer.addjButtonRemoveActionListener(l);

        FSupplyList.setVisible(true);
    }
   

    // <editor-fold defaultstate="collapsed" desc="SupplyFGIE">
    private String      title, article;
    private BigDecimal  actualprice;
    private int         amountleft;
    private Category    category;

    //инициализация формы редактирования
    private FormGenericItemEditor configureSupplyFGIE(String title) {
        FormGenericItemEditor FGIE = new FormGenericItemEditor(4, 1);
        List<JTextField> list = FGIE.getTextFields();
        list.get(0).setBorder(BorderFactory.createTitledBorder(PropsUtil.getProperty("Supply.Title")));
        list.get(1).setBorder(BorderFactory.createTitledBorder(PropsUtil.getProperty("Supply.Article")));
        list.get(2).setBorder(BorderFactory.createTitledBorder(PropsUtil.getProperty("Supply.AmountLeft")));
        list.get(3).setBorder(BorderFactory.createTitledBorder(PropsUtil.getProperty("Supply.ActualPrice")));
        FGIE.getComboBoxes().get(0).setBorder(BorderFactory.createTitledBorder(PropsUtil.getProperty("Supply.Category")));
        FGIE.getComboBoxes().get(0).setModel(new GenericListModel<Category>((DaoFactory.getCategoryDao().getList())));
        //FGIE.addjButtonOkActionListener(OkActionListener);
        FGIE.setTitle(title);
        FGIE.setVisible(true);
        return FGIE;
    }

    //получить параметры из формы в глобальные переменные этого класса
    private boolean  EndSupplyFGIE(FormGenericItemEditor FGIE){
        title = FGIE.getTextFields().get(0).getText();
        article = FGIE.getTextFields().get(1).getText();
        //amountleft = Integer.valueOf(FGIE.getTextFields().get(2).getText());
        String sp = FGIE.getTextFields().get(3).getText();
        if (title.equals("") || sp.equals("")){
            JOptionPane.showMessageDialog(FGIE, "Не все поля заполнены", FGIE.getTitle(), JOptionPane.ERROR_MESSAGE);
            return false;//выход до вызова dispose, возврат к форме редактирования
        }
        try {
            actualprice = new BigDecimal(sp);
            if (!FGIE.getTextFields().get(2).getText().equals(""))
                amountleft = Integer.valueOf(FGIE.getTextFields().get(2).getText());
            else amountleft = 0;
        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(FGIE, "Числовые поля заполнены некорректно", FGIE.getTitle(), JOptionPane.ERROR_MESSAGE);
            return false;//выход до вызова dispose, возврат к форме редактирования
        }
        category = (Category) FGIE.getComboBoxes().get(0).getSelectedItem();
        FGIE.dispose();
        return true;
    }

    public void StartAddSupply(){
        final FormGenericItemEditor FGIE = configureSupplyFGIE("New Supply");
        ActionListener l = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (EndSupplyFGIE(FGIE))
                    EndAddSupply();
            }
        };
        FGIE.addjButtonOkActionListener(l);
    }


    private void EndAddSupply(){
      try {

        Supply s = DaoFactory.getSupplyDao().create(SupplyDao.createPK("Тихонов", title), 0, actualprice);
        s.setAmountLeft(amountleft);
        DaoFactory.getSupplyDao().update(s);
        s.getProduct().setArticle(article);
        s.getProduct().setCategory(category);
        DaoFactory.getProductDao().update(s.getProduct());
        ListSupplies.add(s);
        FSupplyList.getJPanelSupplyList().getTable().revalidate();
        } catch (DaoException ex) {
            Logger.getLogger(SupplyListTool.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(FSupplyList, ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void StartEditSupply(){
        final FormGenericItemEditor FGIE = configureSupplyFGIE("Edit Supply");
        ActionListener l = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (EndSupplyFGIE(FGIE))
                    EndEditSupply();
            }
        };
        FGIE.addjButtonOkActionListener(l);
        Supply p = FSupplyList.getSelectedPrice();
        FGIE.getTextFields().get(0).setText(p.getProduct().getTitle());
        FGIE.getTextFields().get(1).setText(p.getProduct().getArticle());
        String s = null;
        if (p.getAmountLeft() != null) s = p.getAmountLeft().toString();
        FGIE.getTextFields().get(2).setText(s);
        FGIE.getTextFields().get(3).setText(p.getActualPrice().toString());
        FGIE.getComboBoxes().get(0).setSelectedItem(p.getProduct().getCategory());
    }

    public void EndEditSupply(){
//        try {
            Supply buf = FSupplyList.getSelectedPrice();
            if (!actualprice.equals(buf.getActualPrice()))
                    buf.setPrevPrice(buf.getActualPrice());
            buf.setActualPrice(actualprice);
            buf.setAmountLeft(amountleft);
            Product pr = buf.getProduct();
            //pr.setCategory(category);
            DaoFactory.getProductDao().update(pr, title);
            DaoFactory.getProductDao().update(pr, article, category);
            DaoFactory.getSupplyDao().update(buf);
            FSupplyList.getJPanelSupplyList().getTable().revalidate();
//        } catch (DaoException ex) {
//            Logger.getLogger(SupplyListTool.class.getName()).log(Level.SEVERE, null, ex);
//            JOptionPane.showMessageDialog(FSupplyList, ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
//        }
    }

    public void StartDeleteSupply(){
        Supply s = FSupplyList.getSelectedPrice();
        if (JOptionPane.showConfirmDialog(FSupplyList, "Вы уверены, что хотите удалить " + s.getProduct().getTitle()
                + "?\n", "Удаление", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
        DaoFactory.getSupplyDao().delete(s);
        ListSupplies.remove(s);
        FSupplyList.getJPanelSupplyList().getTable().revalidate();
    }

    // </editor-fold>

    public static void main(String[] args) {
        try {
           new SupplyListTool();
        } catch (JDBCConnectionException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }       
    }
}