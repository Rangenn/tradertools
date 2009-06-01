/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;


import dao.DaoFactory;
import dao.SupplyDao;
import entity.Category;
import entity.Supply;
import entity.SupplyPK;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import org.hibernate.exception.JDBCConnectionException;
import util.PropsUtil;
import view.FormTransmitter;

/**
 *
 * @author е
 */


public class DataTransmitTool {

    public static final int MODE_IMPORT = 0 ;
    public static final int MODE_EXPORT = 1 ;

    private FormTransmitter FTransmitter;

    public DataTransmitTool(){
        try{
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }
        catch (Exception ex){
            System.out.println(ex.toString());
        }

        FTransmitter = new FormTransmitter();

        ActionListener l = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                TransmitPrices();
            }
        };
        FTransmitter.addjButtonRunActionListener(l);

        FTransmitter.setVisible(true);
    }

    //создать конструктор, не включающий форму, но получающий необходимые параметры
    //для последующего вызова методов импорта или экспорта


    public void TransmitPrices(){
        String filename = FTransmitter.getFileName();
        int mode = FTransmitter.getTransmitMode();
        int DocType = FTransmitter.getDocumentType();
        switch (mode)
        {
            case MODE_EXPORT: { ExportPriceList(filename); break; }
            case MODE_IMPORT: { ImportPriceList(filename, DocType); break; }
        }
        
    }

    public void ImportPriceList(String filename, int type){
        String[][] data = null;
        Exception e = null;
        ArrayList errors = new ArrayList();
        //импортируем весь документ
        try {
            data = new excel.Transmitter().Import(filename);
        } catch (IOException ex) {
            Logger.getLogger(DataTransmitTool.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(FTransmitter, PropsUtil.getProperty("IOException"));
            return;
        }

        int ctr = 0;
        String category = null;
        Category cat;
        Supply s;
        BigDecimal a;
        //разбираем импортированные данные и добавляем в базу

        if (type == 0) {
            for (int i = 0; i < data.length; i++) {
                try {
                    if (data[i].length < 4)
                        category = null;
                    else category = data[i][3];
                    cat = DaoFactory.getCategoryDao().create(category);
                    //try {
                    //= (data[i][2] == null || data[i][2].equals("")) ? null :
                    a = new BigDecimal(data[i][2]);
                    //}
                    //catch (NumberFormatException ex) { a = null; }
                    if (DaoFactory.getProductDao().exists(data[i][0])) {
                        SupplyPK id = SupplyDao.createPK(
                            DaoFactory.getCustomerDao().getMySelf(), data[i][0]);
                        if (DaoFactory.getSupplyDao().exists(id)) {
                            s = DaoFactory.getSupplyDao().read(id);
                            if (a.equals(s.getPrice())) {
                                s.setPrice(a);
                                 DaoFactory.getSupplyDao().update(s);//обновление
                            }
                        }
                        else s = DaoFactory.getSupplyDao().create(id, a);  //создание
                        s.getProduct().setArticle(data[i][1]);
                        s.getProduct().setCategory(cat);
                        DaoFactory.getProductDao().update(s.getProduct());
                    }
                }
                catch (Exception ex){
                    ctr++;
                    errors.add(i);
                    e = ex;
                    //Logger.getLogger(DataTransmitTool.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        else if (type == 1) {
            for (int i = 8; i < data.length; i++) {
                try {
                    //try {
                    //= (data[i][2] == null || data[i][2].equals("")) ? null :
                    a = new BigDecimal(data[i][3]);
                    //}
                    //catch (NumberFormatException ex) { a = null; }
                    s = DaoFactory.getSupplyDao().create(SupplyDao.createPK(
                            DaoFactory.getCustomerDao().get("Ока-Серпухов"), data[i][0]), a);//?! хардкод
                    s.setAmountLeft(Double.valueOf(data[i][4]).intValue());
                    DaoFactory.getProductDao().update(s.getProduct());
                }
                catch (Exception ex){
                    ctr++;
                    e = ex;
                    //Logger.getLogger(DataTransmitTool.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }


        //выводим результаты импорта
        System.out.println("ImportPriceList result:\n");
        System.out.println("Imported: " + String.valueOf(data.length - ctr) + '\n');
        System.out.println("Skipped with error: " + String.valueOf(ctr) + '\n');
        System.out.println("Error strings: " + errors.toString());
        if (e != null) {
            System.out.println("Last error: ");
            e.printStackTrace();
            System.out.println("Last error's end.");
        }
    }

    public void ExportPriceList(String filename){

    }

    public static void main(String[] args) {
        try {
           new DataTransmitTool();
        } catch (JDBCConnectionException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

}
