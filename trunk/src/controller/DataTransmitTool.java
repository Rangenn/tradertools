/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import dao.PriceDao;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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
        switch (mode)
        {
            case MODE_EXPORT: { ExportPrices(filename); break; }
            case MODE_IMPORT: { ImportPrices(filename); break; }
        }
        
    }

    public void ImportPrices(String filename){
        String[][] data = null;
        PriceDao PriceDao = new PriceDao();

        try {
            data = new excel.Transmitter().Import(filename);
        } catch (IOException ex) {
            Logger.getLogger(DataTransmitTool.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(FTransmitter, PropsUtil.getProperty("IOException"));
            return;
        }

        int ctr = 0;
        for (int i = 0; i < data.length; i++) {
//            Query =
//                "INSERT INTO product (title, article) " +
//                "VALUES ('" + data[i][0] + "','" + data[i][1] + "')";
//            HibUtil.getSession().createSQLQuery(Query).executeUpdate();
            try {
                PriceDao.create(data[i][0], data[i][0], null, data[i][2], null);
            }
            catch (Exception ex){//test
                ctr++;
                //Logger.getLogger(DataTransmitTool.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("ImportPrices result:\n");
        System.out.println("Imported: " + String.valueOf(data.length - ctr) + '\n');
        System.out.println("Skipped with error: " + String.valueOf(ctr) + '\n');
    }

    public void ExportPrices(String filename){

    }

    public static void main(String[] args) {
        try {
           new DataTransmitTool();
        } catch (JDBCConnectionException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

}
