/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Generico;

import java.util.Map;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

public class listarReportes {

    static GenericoConexion conexion;

    public static void imprimir(String pNombreReporte, String pNombreExport, Map parametros) {
        try {

            JDialog dialogoReport = new JDialog(new JFrame(), "Reporte Antomotores", true);
            dialogoReport.setSize(824, 568);
            dialogoReport.setLocationRelativeTo(null);

            conexion = new GenericoConexion();
            JasperReport report = JasperCompileManager.compileReport(pNombreReporte);
            JasperPrint print = JasperFillManager.fillReport(report, parametros, conexion.Conexion);
            // Exporta el informe a PDF
            JasperExportManager.exportReportToPdfFile(print, pNombreExport);

            //Para visualizar el pdf directamente desde java
            dialogoReport.getContentPane().add(new JasperViewer(print, true).getContentPane());
            dialogoReport.setVisible(true);

        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null,
                    "Error al Generar el Reporte. " + ex.getMessage());
        }
    }
}
