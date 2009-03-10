/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import dao.CategoryDao;
import dao.DaoException;
import dao.PriceDao;
import dao.ProductDao;
import entity.*;
import java.awt.FocusTraversalPolicy;
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
import util.HibUtil;
import util.PropsUtil;
import view.*;
import view.componentmodels.CMCategory;
/**
 *
 * @author е
 */
public class PriceListTool {

    FormPriceList FPriceList;
    FormProductsList FProductsList;
    List<Price> ListPrices;
    PriceDao PriceDao;
    ProductDao ProductDao;
    CategoryDao CategoryDao;

    public PriceListTool(){
        try{
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch (Exception ex){
            System.out.println(ex.toString());
        }        

        PriceDao = new PriceDao();
        ProductDao = new ProductDao();
        CategoryDao = new CategoryDao();
        ListPrices = PriceDao.getList();

        FPriceList = new FormPriceList(ListPrices);

        ActionListener l = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StartAddPrice();
            }
        };
        FPriceList.addjMenuItemAddActionListener(l);

        l = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StartEditPrice();
            }
        };
        FPriceList.addjMenuItemEditActionListener(l);

        l = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StartDeletePrice();
            }
        };
        FPriceList.addjMenuItemDeleteActionListener(l);

        DocumentListener dl = new DocumentListenerSearch();
        FPriceList.getJPanelSearch().addjTextFieldSearchTemplateDocumentListener(dl);

        l = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = 0;
                try {
                    index = FPriceList.getSelectedPriceIndex() + 1;
                    if (index == ListPrices.size()) index = 0;
                } catch (ArrayIndexOutOfBoundsException ex) {
                    Logger.getLogger(PriceListTool.class.getName()).log(Level.WARNING, ex.getMessage(), ex);
                }
                SearchNext(FPriceList.getJPanelSearch().getSearchTemplate(), index);
            }
        };
        FPriceList.getJPanelSearch().addjButtonNextActionListener(l);

        l = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = ListPrices.size() - 1;
                try {
                    index = FPriceList.getSelectedPriceIndex() - 1;
                    if (index == -1) index = ListPrices.size() - 1;
                } catch (ArrayIndexOutOfBoundsException ex) {
                    Logger.getLogger(PriceListTool.class.getName()).log(Level.WARNING, ex.getMessage(), ex);
                }
                SearchPrev(FPriceList.getJPanelSearch().getSearchTemplate(), index);
            }
        };
        FPriceList.getJPanelSearch().addjButtonPrevActionListener(l);

        FPriceList.setVisible(true);
    }

    public void Search(DocumentEvent e)
    {
        try {
            SearchNext(e.getDocument().getText(0, e.getDocument().getLength()), 0);
        } catch (BadLocationException ex) {
            Logger.getLogger(PriceListTool.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    //SearchNext и SearchPrev требуют рефакторинга!
    public void SearchNext(String template, int pos)
    {
        if (template == null || template.equals("")) return;
        if (pos < 0 || pos >= ListPrices.size()) return;
        for(int i = pos; i < ListPrices.size(); i++){
            if (ListPrices.get(i).getProductId().getTitle().toLowerCase().startsWith(template.toLowerCase())){
                FPriceList.setSelectedPrice(i);
                FPriceList.getJPanelSearch().setHavingResult(true);
                return;
            }
        }
        for(int i = 0; i < pos; i++)
            if (ListPrices.get(i).getProductId().getTitle().toLowerCase().startsWith(template.toLowerCase())){
                FPriceList.setSelectedPrice(i);
                FPriceList.getJPanelSearch().setHavingResult(true);
                return;
            }
        
        FPriceList.getJPanelSearch().setHavingResult(false);
    }

    public void SearchPrev(String template, int pos)
    {
        if (template == null || template.equals("")) return;
        if (pos < 0 || pos >= ListPrices.size()) return;
        for(int i = pos; i >= 0; i--){
            if (ListPrices.get(i).getProductId().getTitle().toLowerCase().startsWith(template.toLowerCase())){
                FPriceList.setSelectedPrice(i);
                FPriceList.getJPanelSearch().setHavingResult(true);
                return;
            }
        }
        for(int i = ListPrices.size() - 1; i > pos; i--)
            if (ListPrices.get(i).getProductId().getTitle().toLowerCase().startsWith(template.toLowerCase())){
                FPriceList.setSelectedPrice(i);
                FPriceList.getJPanelSearch().setHavingResult(true);
                return;
            }
        FPriceList.getJPanelSearch().setHavingResult(false);
    }

    // <editor-fold defaultstate="collapsed" desc="PriceFGIE">
    private String      title, article;
    private BigDecimal  buyingprice, sellingprice;
    private Category    category;

    //инициализация формы редактирования
    private FormGenericItemEditor configurePriceFGIE(String title) {
        FormGenericItemEditor FGIE = new FormGenericItemEditor(4, 1);
        List<JTextField> list = FGIE.getTextFields();
        list.get(0).setBorder(BorderFactory.createTitledBorder(PropsUtil.getProperty("Price.Title")));
        list.get(1).setBorder(BorderFactory.createTitledBorder(PropsUtil.getProperty("Price.Article")));
        list.get(2).setBorder(BorderFactory.createTitledBorder(PropsUtil.getProperty("Price.BuyingPrice")));
        list.get(3).setBorder(BorderFactory.createTitledBorder(PropsUtil.getProperty("Price.SellingPrice")));
        FGIE.getComboBoxes().get(0).setBorder(BorderFactory.createTitledBorder(PropsUtil.getProperty("Price.Category")));
        FGIE.getComboBoxes().get(0).setModel(new CMCategory((CategoryDao.getList())));
        //FGIE.addjButtonOkActionListener(OkActionListener);
        FGIE.setTitle(title);
        FGIE.setVisible(true);
        return FGIE;
    }

    //получить параметры из формы в глобальные переменные этого класса
    private boolean  EndPriceFGIE(FormGenericItemEditor FGIE){
        title = FGIE.getTextFields().get(0).getText();
        article = FGIE.getTextFields().get(1).getText();
        String bp = FGIE.getTextFields().get(2).getText();
        String sp = FGIE.getTextFields().get(3).getText();
        if (title.equals("") || sp.equals("")){
            JOptionPane.showMessageDialog(FGIE, "Не все поля заполнены", FGIE.getTitle(), JOptionPane.ERROR_MESSAGE);
            return false;//выход до вызова dispose, возврат к форме редактирования
        }
        try {
            buyingprice = (bp == null || bp.equals("")) ? null : new BigDecimal(bp);
            sellingprice = (sp == null || sp.equals("")) ? null : new BigDecimal(sp);
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
        try {
            Price p = PriceDao.create(title, article, buyingprice, sellingprice, category);
            ListPrices.add(p);
            FPriceList.getJPanelPriceList().getTable().revalidate();
        } catch (DaoException ex) {
            Logger.getLogger(PriceListTool.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(FPriceList, ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
        }        
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
        Price p = FPriceList.getSelectedPrice();
        FGIE.getTextFields().get(0).setText(p.getProductId().getTitle());
        FGIE.getTextFields().get(1).setText(p.getProductId().getArticle());
        String s = null;
        if (p.getBuyingPrice() != null) s = p.getBuyingPrice().toString();
        FGIE.getTextFields().get(2).setText(s);
        FGIE.getTextFields().get(3).setText(p.getSellingPrice().toString());
        FGIE.getComboBoxes().get(0).setSelectedItem(p.getProductId().getCategoryId());
    }

    public void EndEditPrice(){
//        try {
            PriceDao.update(FPriceList.getSelectedPrice(), title, article, buyingprice, sellingprice, category);
            FPriceList.getJPanelPriceList().getTable().revalidate();
//        } catch (DaoException ex) {
//            Logger.getLogger(PriceListTool.class.getName()).log(Level.SEVERE, null, ex);
//            JOptionPane.showMessageDialog(FPriceList, ex.getMessage(), "", JOptionPane.ERROR_MESSAGE);
//        }
    }

    public void StartDeletePrice(){
        DeletePrice(FPriceList.getSelectedPrice());
    }

    public void DeletePrice(Price o){
        if (JOptionPane.showConfirmDialog(FPriceList, "Вы уверены, что хотите удалить " + o.getProductId().getTitle()
                + "?\n", "Удаление", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION)
        PriceDao.delete(o);
        ListPrices.remove(o);
        FPriceList.getJPanelPriceList().getTable().revalidate();
    }

    // </editor-fold>

    public static void main(String[] args) {
        try {
           new PriceListTool();
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
