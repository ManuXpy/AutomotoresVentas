/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Generico;

import java.util.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author juan
 */
public class Genericos {

    private TableRowSorter claseFiltro;
    private ResultSet cursor;
    private GenericoConexion conexion = new GenericoConexion();

    public static String AntInyectSQL(Object sintanxis) {
        String[] out = {"select", "union", "drop", "delete", "insert", "update", "truncate", "commit", "rollback", "=", "=or", "=and", "function", "procedure", "trigger", "create", "replace", "database", "unique", "join", "left", "right", "full", "inner"};
        for (String regla : out) {
            if (sintanxis.toString().toLowerCase().contains(regla)) {
                System.out.println(sintanxis + " ~ contiene ~ " + regla);
                GenericoConexion.SqlAntInyeccion = true;
                return "./-";
            }
        }
        return "" + sintanxis + "";
    }

    public static boolean verificarTxt(JTextField txt) {
        if (txt.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(txt, "No Puede Estar Vacio", "Aviso al usuario", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        return true;
    }

    public static boolean verificarCombo(JComboBox combo) {
        if (combo.getSelectedIndex() <= 0) {
            JOptionPane.showMessageDialog(combo, "debe seleccionar una opcion", "Aviso al usuario", JOptionPane.INFORMATION_MESSAGE);
            return false;
        } else {
        }
        return true;
    }

    public void setearTablaBuscador(JTable Grilla) {
        DefaultTableModel tabla = (DefaultTableModel) Grilla.getModel();
        claseFiltro = new TableRowSorter(tabla);
        Grilla.setRowSorter(claseFiltro);
    }

    public void nuevoFiltro(JTextField txt) {
        RowFilter<DefaultTableModel, Object> rf;
        try {
            rf = RowFilter.regexFilter(txt.getText(), 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11);
        } catch (java.util.regex.PatternSyntaxException e) {
            return;
        }
        claseFiltro.setRowFilter(rf);
    }

    public boolean verificarFilas(JTable grilla, JTextField txt) {
        String descripExistente;
        for (int i = 0; i < grilla.getRowCount(); i++) {
            descripExistente = grilla.getValueAt(i, 1).toString();
            if (txt.getText().trim().toLowerCase().equals(descripExistente)) {
                JOptionPane.showMessageDialog(null, "Descripcion Existente");
                return false;
            }
        }
        return true;
    }

    public String autonumerico(String nombreColumna, String nombreTabla) {
        String sql = "SELECT Coalesce(MAX(" + nombreColumna + "),0) + 1 AS alias FROM " + nombreTabla + "";
        cursor = conexion.ejecutarResultSet(sql);
        try {
            if (cursor.next()) {
                return String.valueOf(cursor.getInt("alias"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Genericos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }

    public ArrayList preparaDatosTabla(String consultaSQL) throws SQLException {
        ResultSetMetaData modeloDatos;
        ArrayList arrayLocal = new ArrayList();
        cursor = conexion.ejecutarResultSet(consultaSQL);
        modeloDatos = cursor.getMetaData();
        while (cursor.next()) {
            int nroColumna = modeloDatos.getColumnCount();
            Object[] datosFila = new Object[nroColumna];
            for (int i = 0; i < nroColumna; i++) {
                System.out.println(cursor.getObject(i + 1));
                datosFila[i] = cursor.getObject(i + 1);
            }
            arrayLocal.add(datosFila);
        }
        return arrayLocal;
    }

    public void limpiarCampos(JPanel jPanel) {
        for (int i = 0; jPanel.getComponents().length > i; i++) {
            //Limpia todos los JTextField de un JPanel
            if (jPanel.getComponents()[i] instanceof JTextField) {
                ((JTextField) jPanel.getComponents()[i]).setText(null);
            } //Limpia todos los JPasswordField de un JPanel
            if (jPanel.getComponents()[i] instanceof JPasswordField) {
                ((JPasswordField) jPanel.getComponents()[i]).setText(null);
                // Limpia todos los JFormattedTextField de un panel
            } else if (jPanel.getComponents()[i] instanceof JFormattedTextField) {
                ((JFormattedTextField) jPanel.getComponents()[i]).setText(null);
            }
        }

    }

    public static void apariencia() {
        try {

            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);
            UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaSimple2DLookAndFeel");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getFechaServidor() {

        conexion = new GenericoConexion();

        ResultSet rs;
        try {
            rs = conexion.ejecutarResultSet("SELECT date_format(current_date, '%d/%m/%Y') as fecha;");
            rs.next();
            return rs.getString("Fecha");
        } catch (Throwable e) {

            return "Fecha Inválida";
        }
    }

    public String getFechaFormateadaYYYYMMDD(String fecha) throws ParseException {//DD/MM/YYYY
        SimpleDateFormat sdfecha = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat fechaFormato_BD = new SimpleDateFormat("yyyy/MM/dd");
        Date Desde_f = null;
        Desde_f = sdfecha.parse(fecha);// DD/MM/YYYY
        String fec_compra = fechaFormato_BD.format(Desde_f);
        return fec_compra;
    }

    //RETORNA FECHA FORMATO ddMMyyyy
    public String retornaFechaddMMyyyy(Date fecha) {
        SimpleDateFormat fechaFormat = new SimpleDateFormat("dd/MM/yyyy");
        return fechaFormat.format(fecha);
    }

    public void prepararDateField(JFormattedTextField asdfecha) throws java.text.ParseException {
        asdfecha.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("##/##/####")));
    }

}
