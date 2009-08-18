/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import java.sql.Connection;
import java.util.HashMap;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import org.apache.log4j.Logger;

/**
 *
 * @author е
 */
public class ReportUtil {

//    public static JasperReport compileReport(String jrXml) throws JRException {
//        return JasperCompileManager.compileReport(jrXml);
//    }

    /**
     * Скомпилировать отчет на основе шаблона,
     * заполнить его, используя набор параметров и соединение с БД,
     * сохранить результат в pdf файл.
     * @param jrXml Xml шаблон отчета
     * @param parameters Набор параметров, используемых в шаблоне. Может быть пустым.
     * @param connection Соединение. Используется в отчетах, содержащих запросы к БД.
     * @param reportFilename Имя файла-отчета - результата работы метода. может содержать путь к файлу.
     * @throws net.sf.jasperreports.engine.JRException
     */
    public static void makeReport(String jrXml, HashMap parameters,
            Connection connection, String reportFilename) throws JRException {
        Logger.getLogger(ReportUtil.class).info("makeReport: " + jrXml);
        JasperReport jr = JasperCompileManager.compileReport(jrXml);

        JasperPrint jp = JasperFillManager.fillReport(jr, parameters, connection);

        JasperExportManager.exportReportToPdfFile(jp, reportFilename);
        Logger.getLogger(ReportUtil.class).info("makeReport successfull: \"" + reportFilename + '"');
    }
}
