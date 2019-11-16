/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controles;

import Generico.GenericoConexion;
import Generico.Genericos;
import Vistas.GUI_clientes;
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
public class ControlClientes {

    private GUI_clientes dialoClientes;
    private Generico.GenericoConexion conexion;
    private String sintaxiSql;

    public ControlClientes(JDialog objetoClientes) {
        dialoClientes = (GUI_clientes) objetoClientes;
        conexion = new GenericoConexion();
    }

    public void initControles() {
        //setear cuadros de textos
        dialoClientes.txtCodigo.setEditable(false);
        dialoClientes.txtNombre.setEditable(false);
        dialoClientes.txtRuc.setEditable(false);
        dialoClientes.txtRz.setEditable(false);
        dialoClientes.txtTelf.setEditable(false);

        dialoClientes.txtCodigo.setText(null);
        dialoClientes.txtNombre.setText(null);
        dialoClientes.txtRuc.setText(null);
        dialoClientes.txtRz.setText(null);
        dialoClientes.txtTelf.setText(null);

        dialoClientes.btGrabar.setEnabled(false);
        dialoClientes.btAgregar.requestFocus();
    }

    public void habilitaBotones() {
        dialoClientes.btAgregar.setEnabled(true);
        dialoClientes.btModificar.setEnabled(true);
        dialoClientes.btEliminar.setEnabled(true);
        dialoClientes.btGrabar.setEnabled(false);

    }

    public void desHabilitaBotones() {
        dialoClientes.btAgregar.setEnabled(false);
        dialoClientes.btModificar.setEnabled(false);
        dialoClientes.btEliminar.setEnabled(false);
    }

    public void alta() {
        sintaxiSql = null;
        sintaxiSql = "INSERT INTO clientes (nombre_cliente, razonsocial_cliente, ruc_cliente, telefono_cliente)"
                + " VALUES ('" + Genericos.AntInyectSQL(dialoClientes.txtNombre.getText().trim()) + "',"
                + " '" + Genericos.AntInyectSQL(dialoClientes.txtRz.getText().trim()) + "', "
                + " '" + Genericos.AntInyectSQL(dialoClientes.txtRuc.getText().trim()) + "',"
                + " '" + Genericos.AntInyectSQL(dialoClientes.txtTelf.getText().trim()) + "');";
        System.out.println(sintaxiSql);
        conexion.ejecutarStatemenSet(sintaxiSql);
        //Falto el comit en el ejemplo aparantemte funciona pero cuando miras en la BD te das cuenta que no se ve reflejado la insercion... 
        conexion.comit();
        JOptionPane.showMessageDialog(null, "REGISTRO GUARDADO");

    }

    public void baja() {
        sintaxiSql = null;
        sintaxiSql = "DELETE FROM clientes WHERE  cod_cliente = " + dialoClientes.txtCodigo.getText().trim() + ";";
        conexion.ejecutarStatemenSet(sintaxiSql);
        //Falto el comit en el ejemplo aparantemte funciona pero cuando miras en la BD te das cuenta que no se ve reflejado la insercion... 
        conexion.comit();
        JOptionPane.showMessageDialog(null, "ELIMINACION EXITOSA");

    }

    public void modificacion() {
        sintaxiSql = null;
        sintaxiSql = "UPDATE clientes SET cod_cliente = "
                + Genericos.AntInyectSQL(dialoClientes.txtCodigo.getText().trim())
                + ", nombre_cliente = '" + Genericos.AntInyectSQL(dialoClientes.txtNombre.getText().trim())
                + "', razonsocial_cliente = '" + Genericos.AntInyectSQL(dialoClientes.txtRz.getText().trim())
                + "', ruc_cliente = '" + Genericos.AntInyectSQL(dialoClientes.txtRuc.getText().trim())
                + "', telefono_cliente = '" + Genericos.AntInyectSQL(dialoClientes.txtTelf.getText().trim())
                + "' WHERE cod_cliente = " + Genericos.AntInyectSQL(dialoClientes.txtCodigo.getText().trim()) + ";";
        conexion.ejecutarStatemenSet(sintaxiSql);
        //Falto el comit en el ejemplo aparantemte funciona pero cuando miras en la BD te das cuenta que no se ve reflejado la insercion... 
        conexion.comit();
        JOptionPane.showMessageDialog(null, "REGISTRO MODIFICADO");
    }

    public boolean recuperarRegistro() {
        try {
            ResultSet cursor;
            sintaxiSql = null;
            sintaxiSql = "SELECT cod_cliente, nombre_cliente, razonsocial_cliente, ruc_cliente, telefono_cliente FROM clientes WHERE cod_cliente=" + dialoClientes.txtCodigo.getText().trim() + ";";
            cursor = conexion.ejecutarResultSet(sintaxiSql);
            if (cursor.next()) {
                dialoClientes.txtNombre.setText(cursor.getString("nombre_cliente"));
                dialoClientes.txtRz.setText(cursor.getString("razonsocial_cliente"));
                dialoClientes.txtRuc.setText(cursor.getString("ruc_cliente"));
                dialoClientes.txtTelf.setText(cursor.getString("telefono_cliente"));

                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControlClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void cargarGrilla() {
        DefaultTableModel modeloCargos = (DefaultTableModel) dialoClientes.GrillaClientes.getModel();
        modeloCargos.setRowCount(0);
        try {
            ResultSet cursor;
            sintaxiSql = null;
            sintaxiSql = "SELECT cod_cliente, nombre_cliente, razonsocial_cliente, ruc_cliente, telefono_cliente FROM clientes ORDER BY cod_cliente;";
            cursor = conexion.ejecutarResultSet(sintaxiSql);
            while (cursor.next()) {
                modeloCargos.addRow(new Object[]{
                    cursor.getInt("cod_cliente"),
                    cursor.getString("nombre_cliente"),
                    cursor.getString("razonsocial_cliente"),
                    cursor.getString("ruc_cliente"),
                    cursor.getString("telefono_cliente")});
            }
            dialoClientes.GrillaClientes.setModel(modeloCargos);
        } catch (SQLException ex) {
            Logger.getLogger(ControlClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
