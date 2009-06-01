/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

/**
 *
 * @author е
 */
public class Transmitter {

    /**
     * экспорт строковых данных в Excel-книгу, на 1й лист
     */
    public void Export(String filename, String[][] data) throws IOException{
        //POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filename));
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        for (int i = 0; i < data.length; i++) {
            HSSFRow row = sheet.createRow(i);
            for (int j = 0; j < data[i].length; j++) {
                HSSFCell cell = row.createCell(j);
                cell.setCellValue(new HSSFRichTextString(data[i][j]));
            }
        }

        // Write the output to a file
        FileOutputStream fileOut = new FileOutputStream(filename);
        wb.write(fileOut);
        fileOut.close();

    }

    /**
     * импорт всех строковых данных из excel-книги, 1го листа
     */
    public String[][] Import(String filename) throws IOException{
        String[][] res;
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filename));
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        HSSFSheet sheet = wb.getSheetAt(0);
        int i = 0, j = 0;

        //инициализация массива res
        for (Iterator rit = sheet.rowIterator(); rit.hasNext(); i++){
            rit.next();
        }
        res = new String[i][];
        i = 0;
        //инициализация res[i]
        for (Iterator rit = sheet.rowIterator(); rit.hasNext(); i++){ //считаем длины строк
            HSSFRow row = (HSSFRow)rit.next();
            j = 0;
            for (Iterator cit = row.cellIterator(); cit.hasNext(); j++) {
                cit.next();
            }
            res[i] = new String[j];
        }

        //считывание данных
        i = j = 0;
        for (Iterator rit = sheet.rowIterator(); rit.hasNext(); i++) {
            HSSFRow row = (HSSFRow)rit.next();
            j = 0;
            for (Iterator cit = row.cellIterator(); cit.hasNext(); j++) {
                HSSFCell cell = (HSSFCell)cit.next();
                res[i][j] = cell.toString();
            }
        }
        return res;
    }
}
