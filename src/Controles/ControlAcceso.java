/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controles;

import Generico.GenericoConexion;
import Inicio.acceso;
import Inicio.menu;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;

/**
 *
 * @author ManuXpy
 */
public class ControlAcceso {

    Inicio.acceso dialoAcceso;

    Generico.GenericoConexion conexion;
    ResultSet cursor;
    private String sintaxiSql;

    public ControlAcceso(JDialog dialogoPadre) {

        dialoAcceso = (acceso) dialogoPadre;
        conexion = new GenericoConexion();

    }

    public void initControles() {
        //setear cuadros de textos
        dialoAcceso.txtpass.setEditable(true);
        dialoAcceso.txtpass.setText(null);
        dialoAcceso.txtUsuario.setText(null);
        dialoAcceso.txtUsuario.requestFocus();
    }

    public boolean verificarUsuario() {
        try {
            ResultSet cursor;
            sintaxiSql = null;
            sintaxiSql = "SELECT usuario, id_usuario, pass , estado, perfiles_codigo FROM t_usuarios"
                    + " where usuario='" + Generico.Genericos.AntInyectSQL(dialoAcceso.txtUsuario.getText().trim()) + "'"
                    + "and pass='" + Generico.Genericos.AntInyectSQL(dialoAcceso.txtpass.getText().trim()) + "' "
                    + "and estado=1";

            cursor = conexion.ejecutarResultSet(sintaxiSql);
            if (cursor.next()) {
                menu.perfilUsuario = cursor.getInt("perfiles_codigo");
                menu.usuarioLogueado = cursor.getString("usuario");
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControlAcceso.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
