/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package excel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author е
 */
public class TransmitterTest {

    public TransmitterTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    String filename = "test/excel/test.xls";
    /**
     * Test of Export method, of class Transmitter.
     */
    @Test
    public void testExport() throws Exception {
        System.out.println("Export");
        
        String[][] data = new String[3][];
        data[0] = new String[] {"1","2"};
        data[1] = new String[] {};
        data[2] = new String[] {"3","4","5"};
        Transmitter instance = new Transmitter();
        instance.Export(filename, data);

        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filename));
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        HSSFSheet sheet = wb.getSheetAt(0);
        String msg = "сравнение значений ячеек";
        for(int i = 0; i < data.length; i++)
            for (int j = 0; j < data[i].length; j++)
                assertEquals(msg, data[i][j], sheet.getRow(i).getCell(j).toString());
    }

    /**
     * Test of Import method, of class Transmitter.
     */
    @Test
    public void testImport() throws Exception {
        System.out.println("Import");
        Transmitter instance = new Transmitter();
        String[][] data = new String[3][];
        data[0] = new String[] {"1","2"};
        data[1] = new String[] {};
        data[2] = new String[] {"3","4","5"};
        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filename));
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        HSSFSheet sheet = wb.getSheetAt(0);
        for(int i = 0; i < data.length; i++)
            for (int j = 0; j < data[i].length; j++)
                sheet.getRow(i).getCell(j).setCellValue(data[i][j]);
        FileOutputStream fileOut = new FileOutputStream(filename);
        wb.write(fileOut);
        fileOut.close();
        String msg = "Сравнение массива-результата с ожидаемым";
        String[][] result = instance.Import(filename);
        assertArrayEquals(msg, data, result);
    }
}