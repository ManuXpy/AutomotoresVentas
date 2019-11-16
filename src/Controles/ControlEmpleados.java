/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controles;

import Generico.GenericoConexion;
import Generico.Genericos;
import Vistas.GUI_empleados;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author emanu
 */
public class ControlEmpleados {

    private GUI_empleados dialoEmpleados;
    private Generico.GenericoConexion conexion;
    private String sintaxiSql;
    private final Generico.Genericos utiles = new Genericos();

    public enum opcionesDeRecuperar {
        recuperarCargo

    }

    public ControlEmpleados(JDialog objetoEmpleados) {
        dialoEmpleados = (GUI_empleados) objetoEmpleados;
        conexion = new GenericoConexion();

    }

    public void setearfecha() throws ParseException {
        dialoEmpleados.btAgregar.requestFocus();
        utiles.prepararDateField(dialoEmpleados.txtFechanac);

    }

    public void initControles() {

        //setear cuadros de textos
        dialoEmpleados.txtCodigo.setEditable(false);
        dialoEmpleados.txtNombre.setEditable(false);
        dialoEmpleados.txtApellido.setEditable(false);
        dialoEmpleados.txtDireccion.setEditable(false);
        dialoEmpleados.txtTelf.setEditable(false);
        dialoEmpleados.txtFechanac.setEditable(false);
        dialoEmpleados.txtNroci.setEditable(false);
        dialoEmpleados.txtCargo.setEditable(false);
        dialoEmpleados.txtCodigoCargo.setEditable(false);

        dialoEmpleados.txtCodigo.setText(null);
        dialoEmpleados.txtNombre.setText(null);
        dialoEmpleados.txtApellido.setText(null);
        dialoEmpleados.txtDireccion.setText(null);
        dialoEmpleados.txtTelf.setText(null);

        dialoEmpleados.txtNroci.setText(null);
        dialoEmpleados.txtCodigoCargo.setText(null);

        dialoEmpleados.btGrabar.setEnabled(false);

    }

    public void habilitaBotones() {
        dialoEmpleados.btAgregar.setEnabled(true);
        dialoEmpleados.btModificar.setEnabled(true);
        dialoEmpleados.btEliminar.setEnabled(true);
        dialoEmpleados.btGrabar.setEnabled(false);

    }

    public void desHabilitaBotones() {
        dialoEmpleados.btAgregar.setEnabled(false);
        dialoEmpleados.btModificar.setEnabled(false);
        dialoEmpleados.btEliminar.setEnabled(false);
    }

    public void alta() {
        try {
            sintaxiSql = null;
            String fecha = utiles.getFechaFormateadaYYYYMMDD(dialoEmpleados.txtFechanac.getText());
            sintaxiSql = "INSERT INTO empleados (emple_nombres, emple_apellido, emple_nroci, emple_direccion, empleados_telefono, emple_fechanac, cargos_cod_cargos) VALUES "
                    + "('" + Genericos.AntInyectSQL(dialoEmpleados.txtNombre.getText().trim())
                    + "', '" + Genericos.AntInyectSQL(dialoEmpleados.txtApellido.getText().trim())
                    + "', '" + Genericos.AntInyectSQL(dialoEmpleados.txtNroci.getText().trim())
                    + "', '" + Genericos.AntInyectSQL(dialoEmpleados.txtDireccion.getText().trim())
                    + "', '" + Genericos.AntInyectSQL(dialoEmpleados.txtTelf.getText().trim())
                    + "', '" + Genericos.AntInyectSQL(fecha)
                    + "', " + Genericos.AntInyectSQL(dialoEmpleados.txtCodigoCargo.getText().trim()) + ");";
            System.out.println(sintaxiSql);
            conexion.ejecutarStatemenSet(sintaxiSql);
            //Falto el comit en el ejemplo aparantemte funciona pero cuando miras en la BD te das cuenta que no se ve reflejado la insercion...
            conexion.comit();
            JOptionPane.showMessageDialog(null, "REGISTRO GUARDADO");
        } catch (ParseException ex) {
            Logger.getLogger(ControlEmpleados.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void baja() {
        sintaxiSql = null;
        sintaxiSql = "DELETE FROM empleados WHERE  emple_codigo = " + dialoEmpleados.txtCodigo.getText().trim() + ";";
        conexion.ejecutarStatemenSet(sintaxiSql);
        //Falto el comit en el ejemplo aparantemte funciona pero cuando miras en la BD te das cuenta que no se ve reflejado la insercion... 
        conexion.comit();
        JOptionPane.showMessageDialog(null, "ELIMINACION EXITOSA");

    }

    public void modificacion() {
        try {
            sintaxiSql = null;
            String fecha = utiles.getFechaFormateadaYYYYMMDD(dialoEmpleados.txtFechanac.getText());
            sintaxiSql = "UPDATE empleados SET emple_codigo = "
                    + Genericos.AntInyectSQL(dialoEmpleados.txtCodigo.getText().trim()) + ",  emple_nombres = '"
                    + Genericos.AntInyectSQL(dialoEmpleados.txtNombre.getText().trim()) + "', emple_apellido = '"
                    + Genericos.AntInyectSQL(dialoEmpleados.txtApellido.getText().trim()) + "', emple_nroci = '"
                    + Genericos.AntInyectSQL(dialoEmpleados.txtNroci.getText().trim()) + "', emple_direccion = '"
                    + Genericos.AntInyectSQL(dialoEmpleados.txtDireccion.getText().trim()) + "', empleados_telefono = '"
                    + Genericos.AntInyectSQL(dialoEmpleados.txtTelf.getText().trim()) + "', emple_fechanac = '"
                    + Genericos.AntInyectSQL(fecha) + "', cargos_cod_cargos = "
                    + Genericos.AntInyectSQL(dialoEmpleados.txtCodigoCargo.getText().trim()) + " WHERE emple_codigo = "
                    + Genericos.AntInyectSQL(dialoEmpleados.txtCodigo.getText().trim()) + ";";
            conexion.ejecutarStatemenSet(sintaxiSql);
            //Falto el comit en el ejemplo aparantemte funciona pero cuando miras en la BD te das cuenta que no se ve reflejado la insercion...
            conexion.comit();
            JOptionPane.showMessageDialog(null, "REGISTRO MODIFICADO");
        } catch (ParseException ex) {
            Logger.getLogger(ControlEmpleados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean recuperarRegistro() {
        try {
            ResultSet cursor;
            sintaxiSql = null;
            sintaxiSql = "SELECT empleados.emple_codigo, empleados.emple_nombres, empleados.emple_apellido, empleados.emple_nroci, empleados.emple_direccion, empleados.empleados_telefono, empleados.emple_fechanac, cargos.cargo_descrip, cargos.id_cargos FROM empleados,cargos WHERE empleados.cargos_cod_cargos = cargos.id_cargos AND  empleados.emple_codigo=" + dialoEmpleados.txtCodigo.getText().trim() + ";";
            cursor = conexion.ejecutarResultSet(sintaxiSql);
            if (cursor.next()) {
                dialoEmpleados.txtNombre.setText(cursor.getString("empleados.emple_nombres"));
                dialoEmpleados.txtApellido.setText(cursor.getString("empleados.emple_apellido"));
                dialoEmpleados.txtNroci.setText(cursor.getString("empleados.emple_nroci"));
                dialoEmpleados.txtDireccion.setText(cursor.getString("empleados.emple_direccion"));
                dialoEmpleados.txtTelf.setText(cursor.getString("empleados.empleados_telefono"));
                dialoEmpleados.txtFechanac.setText(utiles.retornaFechaddMMyyyy(cursor.getDate("emple_fechanac")));
                dialoEmpleados.txtCodigoCargo.setText(cursor.getString("cargos.id_cargos"));
                dialoEmpleados.txtCargo.setText(cursor.getString("cargos.cargo_descrip"));

                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControlEmpleados.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void cargarGrilla() {
        DefaultTableModel modeloCargos = (DefaultTableModel) dialoEmpleados.GrillaEmpleados.getModel();
        modeloCargos.setRowCount(0);
        try {
            ResultSet cursor;
            sintaxiSql = null;
            sintaxiSql = "SELECT emple_codigo, emple_nombres, emple_apellido, emple_nroci, emple_direccion, empleados_telefono, emple_fechanac, cargo_descrip FROM empleados,cargos WHERE cargos_cod_cargos = id_cargos ORDER BY emple_codigo;";
            cursor = conexion.ejecutarResultSet(sintaxiSql);
            while (cursor.next()) {
                modeloCargos.addRow(new Object[]{
                    cursor.getInt("emple_codigo"),
                    cursor.getString("emple_nombres"),
                    cursor.getString("emple_apellido"),
                    cursor.getString("emple_nroci"),
                    cursor.getString("emple_direccion"),
                    utiles.retornaFechaddMMyyyy(cursor.getDate("emple_fechanac")),
                    cursor.getString("cargo_descrip"),
                    cursor.getString("empleados_telefono")});
            }
            dialoEmpleados.GrillaEmpleados.setModel(modeloCargos);
        } catch (SQLException ex) {
            Logger.getLogger(ControlEmpleados.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean recuperarRegistro(opcionesDeRecuperar op, JTextField txt) {
        sintaxiSql = null;
        switch (op) {
            case recuperarCargo:
                sintaxiSql = "SELECT id_cargos, cargo_descrip FROM cargos WHERE id_cargos= " + dialoEmpleados.txtCodigoCargo.getText().trim() + "";
                break;
            default:

                break;
        }

        try {
            ResultSet cursor;
            cursor = conexion.ejecutarResultSet(sintaxiSql);
            if (cursor.next()) {

                txt.setText(cursor.getString(2));

                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControlEmpleados.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public ResultSet generarResultSet(opcionesDeRecuperar op) {
        ResultSet cursor;
        sintaxiSql = null;
        switch (op) {
            case recuperarCargo:
                sintaxiSql = "SELECT id_cargos, cargo_descrip FROM cargos";
                break;
        }
        return cursor = conexion.ejecutarResultSet(sintaxiSql);
    }
}
