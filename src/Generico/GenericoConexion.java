/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Generico;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author emanu
 */
public class GenericoConexion {

    public static boolean SqlAntInyeccion = false;
    Connection Conexion = null;
    private final String BD = "ventas";
    private final String Usuario = "root";
    private final String Clave = "";

    //---------- MySQL -------------//
    private final String ControladorBD = "com.mysql.jdbc.Driver";
    private final String Url = "jdbc:mysql://localhost/" + BD;

    //---------- Posdgres ----------//
//    private final String ControladorBD = "org.postgresql.Driver";
//    private final String Url = "jdbc:postgresql://localhost:5432/" + BD;
    
    
    private Statement Statement;
    private ResultSet ResultSet;

    public GenericoConexion() {
        try {
            Class.forName(ControladorBD);
            Conexion = DriverManager.getConnection(Url, Usuario, Clave);
            Statement = Conexion.createStatement();
            Conexion.setAutoCommit(false);

        } catch (ClassNotFoundException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            JOptionPane.showMessageDialog(null, "Error Durante Verificacion del Driver", "AVISO", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error Durante la Conecion", "AVISO", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public void desConectarBD() {
        try {
            Conexion.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Durante la DesConecion", "AVISO", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public ResultSet ejecutarResultSet(String sqlQuery) {
        try {
            if (!SqlAntInyeccion) {
                ResultSet = Statement.executeQuery(sqlQuery);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Durante la operacion", "AVISO", JOptionPane.INFORMATION_MESSAGE);
        }
        return ResultSet;
    }

    public int ejecutarStatemenSet(String sqlQuery) {
        int fila = 0;
        try {
            if (!SqlAntInyeccion) {
                fila = Statement.executeUpdate(sqlQuery);
            }
        } catch (SQLException ex) {
            System.out.println("Error Durante la Ejecucion del ResultSet " + ex.getMessage());
        }
        return fila;
    }

    public void comit() {
        try {
            Conexion.commit();

        } catch (SQLException ex) {
            Logger.getLogger(GenericoConexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void rollback() {
        try {
            Conexion.rollback();
        } catch (SQLException ex) {
            Logger.getLogger(GenericoConexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
