/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controles;

import Generico.GenericoConexion;
import Vistas.GUI_impuestos;
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
public class ControlImpuestos {

    private GUI_impuestos dialoImpuestos;
    private Generico.GenericoConexion conexion;
    private String sintaxiSql;

    public ControlImpuestos(JDialog objetoimpuestos) {
        dialoImpuestos = (GUI_impuestos) objetoimpuestos;
        conexion = new GenericoConexion();
    }

    public void initControles() {
        //setear cuadros de textos
        dialoImpuestos.txtCodigo.setEditable(false);
        dialoImpuestos.txtDescripcion.setEditable(false);

        dialoImpuestos.txtCodigo.setText(null);
        dialoImpuestos.txtDescripcion.setText(null);

        dialoImpuestos.btGrabar.setEnabled(false);
        dialoImpuestos.btAgregar.requestFocus();
    }

    public void habilitaBotones() {
        dialoImpuestos.btAgregar.setEnabled(true);
        dialoImpuestos.btModificar.setEnabled(true);
        dialoImpuestos.btEliminar.setEnabled(true);
        dialoImpuestos.btGrabar.setEnabled(false);

    }

    public void desHabilitaBotones() {
        dialoImpuestos.btAgregar.setEnabled(false);
        dialoImpuestos.btModificar.setEnabled(false);
        dialoImpuestos.btEliminar.setEnabled(false);
    }

    public void alta() {
        sintaxiSql = null;
        sintaxiSql = "INSERT INTO impuesto(descri_impuesto)\n"
                + "VALUES ('" + dialoImpuestos.txtDescripcion.getText().trim() + "');";
        System.out.println(sintaxiSql);
        conexion.ejecutarStatemenSet(sintaxiSql);
        //Falto el comit en el ejemplo aparantemte funciona pero cuando miras en la BD te das cuenta que no se ve reflejado la insercion... 
        conexion.comit();
        JOptionPane.showMessageDialog(null, "REGISTRO GUARDADO");

    }

    public void baja() {
        sintaxiSql = null;
        sintaxiSql = "DELETE FROM impuesto WHERE id_impuesto=" + dialoImpuestos.txtCodigo.getText().trim() + ";";
        conexion.ejecutarStatemenSet(sintaxiSql);
        //Falto el comit en el ejemplo aparantemte funciona pero cuando miras en la BD te das cuenta que no se ve reflejado la insercion... 
        conexion.comit();
        JOptionPane.showMessageDialog(null, "ELIMINACION EXITOSA");

    }

    public void modificacion() {
        sintaxiSql = null;
        sintaxiSql = "UPDATE impuesto SET  descri_impuesto='" + dialoImpuestos.txtDescripcion.getText().trim() + "' WHERE id_impuesto=" + dialoImpuestos.txtCodigo.getText().trim() + ";";
        conexion.ejecutarStatemenSet(sintaxiSql);
        //Falto el comit en el ejemplo aparantemte funciona pero cuando miras en la BD te das cuenta que no se ve reflejado la insercion... 
        conexion.comit();
        JOptionPane.showMessageDialog(null, "REGISTRO MODIFICADO");
    }

    public boolean recuperarRegistro() {
        try {
            ResultSet cursor;
            sintaxiSql = null;
            sintaxiSql = "SELECT  descri_impuesto FROM impuesto WHERE id_impuesto=" + dialoImpuestos.txtCodigo.getText().trim() + ";";
            cursor = conexion.ejecutarResultSet(sintaxiSql);
            if (cursor.next()) {
                dialoImpuestos.txtDescripcion.setText(cursor.getString("descri_impuesto"));
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControlImpuestos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void cargarGrilla() {
        DefaultTableModel modeloCargos = (DefaultTableModel) dialoImpuestos.GrillaImpuestos.getModel();
        modeloCargos.setRowCount(0);
        try {
            ResultSet cursor;
            sintaxiSql = null;
            sintaxiSql = "SELECT id_impuesto, descri_impuesto FROM impuesto ORDER BY id_impuesto;";
            cursor = conexion.ejecutarResultSet(sintaxiSql);
            while (cursor.next()) {
                modeloCargos.addRow(new Object[]{
                    cursor.getInt("id_impuesto"),
                    cursor.getString("descri_impuesto")});
            }
            dialoImpuestos.GrillaImpuestos.setModel(modeloCargos);
        } catch (SQLException ex) {
            Logger.getLogger(ControlImpuestos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
