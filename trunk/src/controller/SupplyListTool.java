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
import view.componentmodel.GenericListModel;
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
        FSupplyList.getJPanelSupplyList().setEditable(true);
        JPanelAddEditRemove aer = FSupplyList.getJPanelSupplyList().getJPanelAddEditRemove();

        ActionListener l = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StartAddSupply();
            }
        };
//        FSupplyList.addjMenuItemAddActionListener(l);
        aer.addjButtonAddActionListener(l); //добавить товар
        l = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StartEditSupply();
            }
        };
        FSupplyList.getJPanelSupplyList().addDoubleClickOnTableListener(l);
        aer.addjButtonEditActionListener(l); //редактировать товар

        l = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StartDeleteSupply();
            }
        };
//        FSupplyList.addjMenuItemDeleteActionListener(l);
        aer.addjButtonRemoveActionListener(l); //удалить товар

        FSupplyList.setVisible(true);
    }
   

    // <editor-fold defaultstate="collapsed" desc="SupplyFGIE">
    private String      title, article;
    private BigDecimal  actualprice;
    private int         amountleft, amountmin, amountToOrder;
    private Category    category;

    //инициализация формы редактирования
    private FormGenericItemEditor configureSupplyFGIE(String title) {
        FormGenericItemEditor FGIE = new FormGenericItemEditor(6, 1);
        List<JTextField> list = FGIE.getTextFields();
        list.get(0).setBorder(BorderFactory.createTitledBorder(PropsUtil.getProperty("Product.Title")));
        list.get(1).setBorder(BorderFactory.createTitledBorder(PropsUtil.getProperty("Product.Article")));
        list.get(2).setBorder(BorderFactory.createTitledBorder(PropsUtil.getProperty("Supply.AmountLeft")));
        list.get(3).setBorder(BorderFactory.createTitledBorder(PropsUtil.getProperty("Supply.ActualPrice")));
        list.get(4).setBorder(BorderFactory.createTitledBorder(PropsUtil.getProperty("Supply.AmountMin")));
        list.get(5).setBorder(BorderFactory.createTitledBorder(PropsUtil.getProperty("Supply.DefaultOrderAmount")));
        FGIE.getComboBoxes().
                get(0).setBorder(BorderFactory.createTitledBorder(PropsUtil.getProperty("Product.Category")));

        FGIE.getComboBoxes().get(0).setModel(new GenericListModel<Category>((DaoFactory.getCategoryDao().getList())));
        //FGIE.addjButtonOkActionListener(OkActionListener);
        FGIE.setTitle(title);
        //FGIE.
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
            if (!FGIE.getTextFields().get(4).getText().equals(""))
                amountmin = Integer.valueOf(FGIE.getTextFields().get(4).getText());
            else amountmin = 0;
            if (!FGIE.getTextFields().get(5).getText().equals(""))
                amountToOrder = Integer.valueOf(FGIE.getTextFields().get(5).getText());
            else amountToOrder = 0;
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

        Supply s = DaoFactory.getSupplyDao().create(SupplyDao.createPK(
                DaoFactory.getCustomerDao().getMySelf(), title), actualprice);
        s.setAmountLeft(amountleft);
        s.setAmountMin(amountmin);
        s.setDefaultOrderAmount(amountToOrder);
        DaoFactory.getSupplyDao().update(s);
        s.getProduct().setArticle(article);
        s.getProduct().setCategory(category);
        DaoFactory.getProductDao().update(s.getProduct());
        ListSupplies.add(s);
        FSupplyList.getJPanelSupplyList().getTable().updateUI();
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
        String s;
        if (p.getAmountLeft() != null) s = p.getAmountLeft().toString(); else s = null;
        FGIE.getTextFields().get(2).setText(s);
        FGIE.getTextFields().get(3).setText(p.getPrice().toString());
        if (p.getAmountMin() != null) s = p.getAmountMin().toString(); else s = null;
        FGIE.getTextFields().get(4).setText(s);
        if (p.getDefaultOrderAmount() != null) s = p.getDefaultOrderAmount().toString(); else s = null;
        FGIE.getTextFields().get(5).setText(s);

        FGIE.getComboBoxes().get(0).setSelectedItem(p.getProduct().getCategory());
    }

    public void EndEditSupply(){
//        try {
            Supply s = FSupplyList.getSelectedPrice();
//            if (!actualprice.equals(s.getPrice()))
//                    s.setPrevPrice(s.getPrice());
            s.setPrice(actualprice);
            s.setAmountLeft(amountleft);
            s.setAmountMin(amountmin);
            s.setDefaultOrderAmount(amountToOrder);
            Product pr = s.getProduct();
            //pr.setCategory(category);
            DaoFactory.getProductDao().update(pr, title);
            DaoFactory.getProductDao().update(pr, article, category);
            DaoFactory.getSupplyDao().update(s);
            FSupplyList.getJPanelSupplyList().getTable().updateUI();
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
        FSupplyList.getJPanelSupplyList().getTable().updateUI();
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