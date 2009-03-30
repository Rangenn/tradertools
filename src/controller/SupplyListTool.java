/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import dao.DaoFactory;
import entity.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import org.hibernate.exception.JDBCConnectionException;
import util.PropsUtil;
import view.*;
import view.componentmodels.CMCategory;
/**
 *
 * @author е
 */
public class SupplyListTool {

    FormSupplyList FSupplyList;
    FormProductsList FProductsList;
    List<Supply> ListSupplies;
    //SupplyDao SupplyDao;
    //ProductDao ProductDao;
    //CategoryDao CategoryDao;

    public SupplyListTool(){
        try{
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch (Exception ex){
            System.out.println(ex.toString());
        }        

        //SupplyDao = new SupplyDao();
        //ProductDao = new ProductDao();
        //CategoryDao = new CategoryDao();
        ListSupplies = DaoFactory.getSupplyDao().getList();

        FSupplyList = new FormSupplyList(ListSupplies);

        ActionListener l = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StartAddPrice();
            }
        };
        FSupplyList.addjMenuItemAddActionListener(l);

        l = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StartEditPrice();
            }
        };
        FSupplyList.addjMenuItemEditActionListener(l);

        l = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StartDeletePrice();
            }
        };
        FSupplyList.addjMenuItemDeleteActionListener(l);

        DocumentListener dl = new DocumentListenerSearch();
        FSupplyList.getJPanelSearch().addjTextFieldSearchTemplateDocumentListener(dl);

        l = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = 0;
                try {
                    index = FSupplyList.getSelectedPriceIndex() + 1;
                    if (index == ListSupplies.size()) index = 0;
                } catch (ArrayIndexOutOfBoundsException ex) {
                    Logger.getLogger(SupplyListTool.class.getName()).log(Level.WARNING, ex.getMessage(), ex);
                }
                SearchNext(FSupplyList.getJPanelSearch().getSearchTemplate(), index);
            }
        };
        FSupplyList.getJPanelSearch().addjButtonNextActionListener(l);

        l = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = ListSupplies.size() - 1;
                try {
                    index = FSupplyList.getSelectedPriceIndex() - 1;
                    if (index == -1) index = ListSupplies.size() - 1;
                } catch (ArrayIndexOutOfBoundsException ex) {
                    Logger.getLogger(SupplyListTool.class.getName()).log(Level.WARNING, ex.getMessage(), ex);
                }
                SearchPrev(FSupplyList.getJPanelSearch().getSearchTemplate(), index);
            }
        };
        FSupplyList.getJPanelSearch().addjButtonPrevActionListener(l);

        FSupplyList.setVisible(true);
    }

    public void Search(DocumentEvent e)
    {
        try {
            SearchNext(e.getDocument().getText(0, e.getDocument().getLength()), 0);
        } catch (BadLocationException ex) {
            Logger.getLogger(SupplyListTool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //SearchNext и SearchPrev требуют рефакторинга!
    public void SearchNext(String template, int pos)
    {
        if (template == null || template.equals("")) return;
        if (pos < 0 || pos >= ListSupplies.size()) return;
        for(int i = pos; i < ListSupplies.size(); i++){
            if (ListSupplies.get(i).getProduct().getTitle().toLowerCase().startsWith(template.toLowerCase())){
                FSupplyList.setSelectedPrice(i);
                FSupplyList.getJPanelSearch().setHavingResult(true);
                return;
            }
        }
        for(int i = 0; i < pos; i++)
            if (ListSupplies.get(i).getProduct().getTitle().toLowerCase().startsWith(template.toLowerCase())){
                FSupplyList.setSelectedPrice(i);
                FSupplyList.getJPanelSearch().setHavingResult(true);
                return;
            }
        
        FSupplyList.getJPanelSearch().setHavingResult(false);
    }

    public void SearchPrev(String template, int pos)
    {
        if (template == null || template.equals("")) return;
        if (pos < 0 || pos >= ListSupplies.size()) return;
        for(int i = pos; i >= 0; i--){
            if (ListSupplies.get(i).getProduct().getTitle().toLowerCase().startsWith(template.toLowerCase())){
                FSupplyList.setSelectedPrice(i);
                FSupplyList.getJPanelSearch().setHavingResult(true);
                return;
            }
        }
        for(int i = ListSupplies.size() - 1; i > pos; i--)
            if (ListSupplies.get(i).getProduct().getTitle().toLowerCase().startsWith(template.toLowerCase())){
                FSupplyList.setSelectedPrice(i);
                FSupplyList.getJPanelSearch().setHavingResult(true);
                return;
            }
        FSupplyList.getJPanelSearch().setHavingResult(false);
    }

    // <editor-fold defaultstate="collapsed" desc="PriceFGIE">
    private String      title, article;
    private BigDecimal  actualprice;
    private int         amountleft;
    private Category    category;

    //инициализация формы редактирования
    private FormGenericItemEditor configurePriceFGIE(String title) {
        FormGenericItemEditor FGIE = new FormGenericItemEditor(4, 1);
        List<JTextField> list = FGIE.getTextFields();
        list.get(0).setBorder(BorderFactory.createTitledBorder(PropsUtil.getProperty("Supply.Title")));
        list.get(1).setBorder(BorderFactory.createTitledBorder(PropsUtil.getProperty("Supply.Article")));
        list.get(2).setBorder(BorderFactory.createTitledBorder(PropsUtil.getProperty("Supply.ActualPrice")));
        list.get(3).setBorder(BorderFactory.createTitledBorder(PropsUtil.getProperty("Supply.AmountLeft")));
        FGIE.getComboBoxes().get(0).setBorder(BorderFactory.createTitledBorder(PropsUtil.getProperty("Supply.Category")));
        FGIE.getComboBoxes().get(0).setModel(new CMCategory((DaoFactory.getCategoryDao().getList())));
        //FGIE.addjButtonOkActionListener(OkActionListener);
        FGIE.setTitle(title);
        FGIE.setVisible(true);
        return FGIE;
    }

    //получить параметры из формы в глобальные переменные этого класса
    private boolean  EndPriceFGIE(FormGenericItemEditor FGIE){
        title = FGIE.getTextFields().get(0).getText();
        article = FGIE.getTextFields().get(1).getText();
        amountleft = Integer.valueOf(FGIE.getTextFields().get(2).getText());
        String sp = FGIE.getTextFields().get(3).getText();
        if (title.equals("") || sp.equals("")){
            JOptionPane.showMessageDialog(FGIE, "Не все поля заполнены", FGIE.getTitle(), JOptionPane.ERROR_MESSAGE);
            return false;//выход до вызова dispose, возврат к форме редактирования
        }
        try {
            actualprice = (sp == null || sp.equals("")) ? null : new BigDecimal(sp);
        }
        catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(FGIE, "Числовые поля заполнены некорректно", FGIE.getTitle(), JOptionPane.ERROR_MESSAGE);
            return false;//выход до вызова dispose, возврат к форме редактирования
        }
        category = (Category) FGIE.getComboBoxes().get(0).getSelectedItem();
        FGIE.dispose();
        return true;
    }

    public void StartAddPrice(){        
        final FormGenericItemEditor FGIE = configurePriceFGIE("New Price");
        ActionListener l = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (EndPriceFGIE(FGIE))
                    EndAddPrice();
            }
        };
        FGIE.addjButtonOkActionListener(l);
    }


    private void EndAddPrice(){               
//        try {
            Supply p = DaoFactory.getSupplyDao().create("Тихонов", title, article, category, 1, actualprice);
            ListSupplies.add(p);
            FSupplyList.getJPanelPriceList().getTable().revalidate();
//        } catch (DaoException ex) {
//            Logger.getLogger(SupplyListTool.class.getName()).log(Level.SEVERE, null, ex);
//            JOptionPane.showMessageDialog(FSupplyList, ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
//        }
    }

    public void StartEditPrice(){
        final FormGenericItemEditor FGIE = configurePriceFGIE("Edit Price");
        ActionListener l = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                if (EndPriceFGIE(FGIE))
                    EndEditPrice();
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

    public void EndEditPrice(){
//        try {
            Supply buf = FSupplyList.getSelectedPrice();
            DaoFactory.getSupplyDao().update(buf, title, article, amountleft, actualprice, category);
            FSupplyList.getJPanelPriceList().getTable().revalidate();
//        } catch (DaoException ex) {
//            Logger.getLogger(SupplyListTool.class.getName()).log(Level.SEVERE, null, ex);
//            JOptionPane.showMessageDialog(FSupplyList, ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
//        }
    }

    public void StartDeletePrice(){
        DeletePrice(FSupplyList.getSelectedPrice());
    }

    public void DeletePrice(Supply o){
        if (JOptionPane.showConfirmDialog(FSupplyList, "Вы уверены, что хотите удалить " + o.getProduct().getTitle()
                + "?\n", "Удаление", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
        DaoFactory.getSupplyDao().delete(o);
        ListSupplies.remove(o);
        FSupplyList.getJPanelPriceList().getTable().revalidate();
    }

    // </editor-fold>

    public static void main(String[] args) {
        try {
           new SupplyListTool();
        } catch (JDBCConnectionException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }       
    }

    private class DocumentListenerSearch implements DocumentListener {

        public DocumentListenerSearch() {
        }

        public void insertUpdate(DocumentEvent e) {
            Search(e);
        }

        public void removeUpdate(DocumentEvent e) {
            Search(e);
        }

        public void changedUpdate(DocumentEvent e) {
            Search(e);
        }
    }

}
