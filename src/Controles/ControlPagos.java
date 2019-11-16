/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controles;

import Generico.GenericoConexion;
import Vistas.GUI_pagos;
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
public class ControlPagos {

    private GUI_pagos dialoPagos;
    private Generico.GenericoConexion conexion;
    private String sintaxiSql;

    public ControlPagos(JDialog objetoCargos) {
        dialoPagos = (GUI_pagos) objetoCargos;
        conexion = new GenericoConexion();
    }

    public void initControles() {
        //setear cuadros de textos
        dialoPagos.txtCodigo.setEditable(false);
        dialoPagos.txtDescripcion.setEditable(false);

        dialoPagos.txtCodigo.setText(null);
        dialoPagos.txtDescripcion.setText(null);

        dialoPagos.btGrabar.setEnabled(false);
        dialoPagos.btAgregar.requestFocus();
    }

    public void habilitaBotones() {
        dialoPagos.btAgregar.setEnabled(true);
        dialoPagos.btModificar.setEnabled(true);
        dialoPagos.btEliminar.setEnabled(true);
        dialoPagos.btGrabar.setEnabled(false);

    }

    public void desHabilitaBotones() {
        dialoPagos.btAgregar.setEnabled(false);
        dialoPagos.btModificar.setEnabled(false);
        dialoPagos.btEliminar.setEnabled(false);
    }

    public void alta() {
        sintaxiSql = null;
        sintaxiSql = "INSERT INTO pagos(descripcion_pago)\n"
                + "VALUES ('" + dialoPagos.txtDescripcion.getText().trim() + "');";
        System.out.println(sintaxiSql);
        conexion.ejecutarStatemenSet(sintaxiSql);
        //Falto el comit en el ejemplo aparantemte funciona pero cuando miras en la BD te das cuenta que no se ve reflejado la insercion... 
        conexion.comit();
        JOptionPane.showMessageDialog(null, "REGISTRO GUARDADO");

    }

    public void baja() {
        sintaxiSql = null;
        sintaxiSql = "DELETE FROM pagos WHERE id_pago=" + dialoPagos.txtCodigo.getText().trim() + ";";
        conexion.ejecutarStatemenSet(sintaxiSql);
        //Falto el comit en el ejemplo aparantemte funciona pero cuando miras en la BD te das cuenta que no se ve reflejado la insercion... 
        conexion.comit();
        JOptionPane.showMessageDialog(null, "ELIMINACION EXITOSA");

    }

    public void modificacion() {
        sintaxiSql = null;
        sintaxiSql = "UPDATE pagos SET  descripcion_pago='" + dialoPagos.txtDescripcion.getText().trim() + "' WHERE id_pago=" + dialoPagos.txtCodigo.getText().trim() + ";";
        conexion.ejecutarStatemenSet(sintaxiSql);
        //Falto el comit en el ejemplo aparantemte funciona pero cuando miras en la BD te das cuenta que no se ve reflejado la insercion... 
        conexion.comit();
        JOptionPane.showMessageDialog(null, "REGISTRO MODIFICADO");
    }

    public boolean recuperarRegistro() {
        try {
            ResultSet cursor;
            sintaxiSql = null;
            sintaxiSql = "SELECT  descripcion_pago FROM pagos WHERE id_pago=" + dialoPagos.txtCodigo.getText().trim() + ";";
            cursor = conexion.ejecutarResultSet(sintaxiSql);
            if (cursor.next()) {
                dialoPagos.txtDescripcion.setText(cursor.getString("descripcion_pago"));
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControlPagos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void cargarGrilla() {
        DefaultTableModel modeloCargos = (DefaultTableModel) dialoPagos.GrillaPagos.getModel();
        modeloCargos.setRowCount(0);
        try {
            ResultSet cursor;
            sintaxiSql = null;
            sintaxiSql = "SELECT id_pago, descripcion_pago FROM pagos ORDER BY id_pago;";
            cursor = conexion.ejecutarResultSet(sintaxiSql);
            while (cursor.next()) {
                modeloCargos.addRow(new Object[]{
                    cursor.getInt("id_pago"),
                    cursor.getString("descripcion_pago")});
            }
            dialoPagos.GrillaPagos.setModel(modeloCargos);
        } catch (SQLException ex) {
            Logger.getLogger(ControlPagos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
