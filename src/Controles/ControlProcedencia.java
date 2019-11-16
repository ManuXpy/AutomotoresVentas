/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controles;

import Generico.GenericoConexion;
import Generico.Genericos;
import Vistas.GUI_procedencia;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author emanu
 */
public class ControlProcedencia {

    private GUI_procedencia dialoProcedencia;
    private Generico.GenericoConexion conexion;
    private String sintaxiSql;

    public ControlProcedencia(JDialog objetoCargos) {
        dialoProcedencia = (GUI_procedencia) objetoCargos;
        conexion = new GenericoConexion();
    }

    public void initControles() {
        //setear cuadros de textos
        dialoProcedencia.txtCodigo.setEditable(false);
        dialoProcedencia.txtDescripcion.setEditable(false);

        dialoProcedencia.txtCodigo.setText(null);
        dialoProcedencia.txtDescripcion.setText(null);

        dialoProcedencia.btGrabar.setEnabled(false);
        dialoProcedencia.btAgregar.requestFocus();
    }

    public void habilitaBotones() {
        dialoProcedencia.btAgregar.setEnabled(true);
        dialoProcedencia.btModificar.setEnabled(true);
        dialoProcedencia.btEliminar.setEnabled(true);
        dialoProcedencia.btGrabar.setEnabled(false);

    }

    public void desHabilitaBotones() {
        dialoProcedencia.btAgregar.setEnabled(false);
        dialoProcedencia.btModificar.setEnabled(false);
        dialoProcedencia.btEliminar.setEnabled(false);
    }

    public void alta() {
        sintaxiSql = null;
        sintaxiSql = "INSERT INTO procedencia (procedencia_descrip) VALUES ('" + Genericos.AntInyectSQL(dialoProcedencia.txtDescripcion.getText().trim()) + "');";
        System.out.println(sintaxiSql);
        conexion.ejecutarStatemenSet(sintaxiSql);
        //Falto el comit en el ejemplo aparantemte funciona pero cuando miras en la BD te das cuenta que no se ve reflejado la insercion... 
        conexion.comit();
        JOptionPane.showMessageDialog(null, "REGISTRO GUARDADO");

    }

    public void baja() {
        sintaxiSql = null;
        sintaxiSql = "DELETE FROM Procedencia WHERE cod_procedencia=" + dialoProcedencia.txtCodigo.getText().trim() + ";";
        conexion.ejecutarStatemenSet(sintaxiSql);
        //Falto el comit en el ejemplo aparantemte funciona pero cuando miras en la BD te das cuenta que no se ve reflejado la insercion... 
        conexion.comit();
        JOptionPane.showMessageDialog(null, "ELIMINACION EXITOSA");

    }

    public void modificacion() {
        sintaxiSql = null;
        sintaxiSql =  "UPDATE procedencia SET cod_procedencia = " + Genericos.AntInyectSQL(dialoProcedencia.txtCodigo.getText().trim()) + ",  procedencia_descrip = '" + Genericos.AntInyectSQL(dialoProcedencia.txtDescripcion.getText().trim()) + "' WHERE cod_procedencia = " + Genericos.AntInyectSQL(dialoProcedencia.txtCodigo.getText().trim()) + ";";
        conexion.ejecutarStatemenSet(sintaxiSql);
        //Falto el comit en el ejemplo aparantemte funciona pero cuando miras en la BD te das cuenta que no se ve reflejado la insercion... 
        conexion.comit();
        JOptionPane.showMessageDialog(null, "REGISTRO MODIFICADO");
    }

    public boolean recuperarRegistro() {
        try {
            ResultSet cursor;
            sintaxiSql = null;
            sintaxiSql = "SELECT  procedencia_descrip FROM Procedencia WHERE cod_procedencia=" + dialoProcedencia.txtCodigo.getText().trim() + ";";
            cursor = conexion.ejecutarResultSet(sintaxiSql);
            if (cursor.next()) {
                dialoProcedencia.txtDescripcion.setText(cursor.getString("procedencia_descrip"));
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControlProcedencia.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void cargarGrilla() {
        DefaultTableModel modeloCargos = (DefaultTableModel) dialoProcedencia.GrillaProcedencia.getModel();
        modeloCargos.setRowCount(0);
        try {
            ResultSet cursor;
            sintaxiSql = null;
            sintaxiSql = "SELECT cod_procedencia, procedencia_descrip FROM Procedencia ORDER BY cod_procedencia;";
            cursor = conexion.ejecutarResultSet(sintaxiSql);
            while (cursor.next()) {
                modeloCargos.addRow(new Object[]{
                    cursor.getInt("cod_procedencia"),
                    cursor.getString("procedencia_descrip")});
            }
            dialoProcedencia.GrillaProcedencia.setModel(modeloCargos);
        } catch (SQLException ex) {
            Logger.getLogger(ControlProcedencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
