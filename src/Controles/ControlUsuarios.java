 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controles;

import Generico.GenericoConexion;
import Vistas.GUI_usuarios;
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
public class ControlUsuarios {

    private GUI_usuarios dialoUsuarios;
    private Generico.GenericoConexion conexion;
    private String sintaxiSql;

    public ControlUsuarios(JDialog objetoUsuarios) {
        dialoUsuarios = (GUI_usuarios) objetoUsuarios;
        conexion = new GenericoConexion();
    }

    public void initControles() {
        //setear cuadros de textos
        dialoUsuarios.txtCodigo.setEditable(false);
        dialoUsuarios.txtUsuario.setEditable(false);
        dialoUsuarios.txtpass.setEditable(false);

        dialoUsuarios.txtCodigo.setText(null);
        dialoUsuarios.txtUsuario.setText(null);
        dialoUsuarios.txtpass.setText(null);

        dialoUsuarios.btGrabar.setEnabled(false);
        dialoUsuarios.btAgregar.requestFocus();
    }

    public void habilitaBotones() {
        dialoUsuarios.btAgregar.setEnabled(true);
        dialoUsuarios.btModificar.setEnabled(true);
        dialoUsuarios.btEliminar.setEnabled(true);
        dialoUsuarios.btGrabar.setEnabled(false);

    }

    public void desHabilitaBotones() {
        dialoUsuarios.btAgregar.setEnabled(false);
        dialoUsuarios.btModificar.setEnabled(false);
        dialoUsuarios.btEliminar.setEnabled(false);
    }

    public void alta() {
        sintaxiSql = null;
        sintaxiSql = "INSERT INTO t_usuarios (usuario, pass) VALUE ('" + dialoUsuarios.txtUsuario.getText().trim() + "', '" + dialoUsuarios.txtpass.getText().trim() + "');";
        System.out.println(sintaxiSql);
        conexion.ejecutarStatemenSet(sintaxiSql);
        //Falto el comit en el ejemplo aparantemte funciona pero cuando miras en la BD te das cuenta que no se ve reflejado la insercion... 
        conexion.comit();
        JOptionPane.showMessageDialog(null, "REGISTRO GUARDADO");

    }

    public void baja() {
        sintaxiSql = null;
        sintaxiSql = "DELETE FROM t_usuarios WHERE id_usuario=" + dialoUsuarios.txtCodigo.getText().trim() + ";";
        conexion.ejecutarStatemenSet(sintaxiSql);
        //Falto el comit en el ejemplo aparantemte funciona pero cuando miras en la BD te das cuenta que no se ve reflejado la insercion... 
        conexion.comit();
        JOptionPane.showMessageDialog(null, "ELIMINACION EXITOSA");

    }

    public void modificacion() {
        sintaxiSql = null;
        sintaxiSql = "UPDATE t_usuarios SET  usuario='" + dialoUsuarios.txtUsuario.getText().trim() + "' , pass= '" + dialoUsuarios.txtpass.getText().trim() + "' WHERE id_usuario=" + dialoUsuarios.txtCodigo.getText().trim() + ";";
        conexion.ejecutarStatemenSet(sintaxiSql);
        //Falto el comit en el ejemplo aparantemte funciona pero cuando miras en la BD te das cuenta que no se ve reflejado la insercion... 
        conexion.comit();
        JOptionPane.showMessageDialog(null, "REGISTRO MODIFICADO");
    }

    public boolean recuperarRegistro() {
        try {
            ResultSet cursor;
            sintaxiSql = null;
            sintaxiSql = "SELECT id_usuario,usuario,pass FROM t_usuarios WHERE id_usuario=" + dialoUsuarios.txtCodigo.getText().trim() + ";";
            cursor = conexion.ejecutarResultSet(sintaxiSql);
            if (cursor.next()) {
                dialoUsuarios.txtUsuario.setText(cursor.getString("usuario"));
                dialoUsuarios.txtpass.setText(cursor.getString("pass"));
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControlUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void cargarGrilla() {
        DefaultTableModel modeloCargos = (DefaultTableModel) dialoUsuarios.GrillaUsuarios.getModel();
        modeloCargos.setRowCount(0);
        try {
            ResultSet cursor;
            sintaxiSql = null;
            sintaxiSql = "SELECT id_usuario,usuario,pass FROM t_usuarios ORDER BY id_usuario;";
            cursor = conexion.ejecutarResultSet(sintaxiSql);
            while (cursor.next()) {
                modeloCargos.addRow(new Object[]{
                    cursor.getInt("id_usuario"),
                    cursor.getString("usuario"),
                    cursor.getString("pass")});
            }
            dialoUsuarios.GrillaUsuarios.setModel(modeloCargos);
        } catch (SQLException ex) {
            Logger.getLogger(ControlUsuarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
