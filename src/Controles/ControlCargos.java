/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controles;

import Generico.GenericoConexion;
import Generico.Genericos;
import Vistas.GUI_cargos;
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
public class ControlCargos {

    private GUI_cargos dialoCargos;
    private Generico.GenericoConexion conexion;
    private String sintaxiSql;

    public ControlCargos(JDialog objetoCargos) {
        dialoCargos = (GUI_cargos) objetoCargos;
        conexion = new GenericoConexion();
    }

    public void initControles() {
        //setear cuadros de textos
        dialoCargos.txtCodigo.setEditable(false);
        dialoCargos.txtDescripcion.setEditable(false);

        dialoCargos.txtCodigo.setText(null);
        dialoCargos.txtDescripcion.setText(null);

        dialoCargos.btGrabar.setEnabled(false);
        dialoCargos.btAgregar.requestFocus();
    }

    public void habilitaBotones() {
        dialoCargos.btAgregar.setEnabled(true);
        dialoCargos.btModificar.setEnabled(true);
        dialoCargos.btEliminar.setEnabled(true);
        dialoCargos.btGrabar.setEnabled(false);

    }

    public void desHabilitaBotones() {
        dialoCargos.btAgregar.setEnabled(false);
        dialoCargos.btModificar.setEnabled(false);
        dialoCargos.btEliminar.setEnabled(false);
    }

    public void alta() {
        sintaxiSql = null;
        sintaxiSql = "INSERT INTO cargos (cargo_descrip) VALUES " + "('" + Genericos.AntInyectSQL(dialoCargos.txtDescripcion.getText().trim()) + "')";
        System.out.println(sintaxiSql);
        conexion.ejecutarStatemenSet(sintaxiSql);
        //Falto el comit en el ejemplo aparantemte funciona pero cuando miras en la BD te das cuenta que no se ve reflejado la insercion... 
        conexion.comit();
        JOptionPane.showMessageDialog(null, "REGISTRO GUARDADO");

    }

    public void baja() {
        sintaxiSql = null;
        sintaxiSql = "DELETE FROM cargos WHERE id_cargos=" + dialoCargos.txtCodigo.getText().trim() + ";";
        conexion.ejecutarStatemenSet(sintaxiSql);
        //Falto el comit en el ejemplo aparantemte funciona pero cuando miras en la BD te das cuenta que no se ve reflejado la insercion... 
        conexion.comit();
        JOptionPane.showMessageDialog(null, "ELIMINACION EXITOSA");

    }

    public void modificacion() {
        sintaxiSql = null;
        sintaxiSql = "UPDATE cargos SET cargo_descrip = '" + Genericos.AntInyectSQL(dialoCargos.txtDescripcion.getText().trim())
                + "' WHERE id_cargos = " + dialoCargos.txtCodigo.getText().trim() + "; ";
        conexion.ejecutarStatemenSet(sintaxiSql);
        //Falto el comit en el ejemplo aparantemte funciona pero cuando miras en la BD te das cuenta que no se ve reflejado la insercion... 
        conexion.comit();
        JOptionPane.showMessageDialog(null, "REGISTRO MODIFICADO");
    }

    public boolean recuperarRegistro() {
        try {
            ResultSet cursor;
            sintaxiSql = null;
            sintaxiSql = "SELECT  cargo_descrip FROM cargos WHERE id_cargos=" + dialoCargos.txtCodigo.getText().trim() + ";";
            cursor = conexion.ejecutarResultSet(sintaxiSql);
            if (cursor.next()) {
                dialoCargos.txtDescripcion.setText(cursor.getString("cargo_descrip"));
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControlCargos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void cargarGrilla() {
        DefaultTableModel modeloCargos = (DefaultTableModel) dialoCargos.GrillaCargos.getModel();
        modeloCargos.setRowCount(0);
        try {
            ResultSet cursor;
            sintaxiSql = null;
            sintaxiSql = "SELECT id_cargos, cargo_descrip FROM cargos ORDER BY id_cargos;";
            cursor = conexion.ejecutarResultSet(sintaxiSql);
            while (cursor.next()) {
                modeloCargos.addRow(new Object[]{
                    cursor.getInt("id_cargos"),
                    cursor.getString("cargo_descrip")});
            }
            dialoCargos.GrillaCargos.setModel(modeloCargos);
        } catch (SQLException ex) {
            Logger.getLogger(ControlCargos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
