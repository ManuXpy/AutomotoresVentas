/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controles;

import Generico.GenericoCombo;
import Generico.GenericoConexion;
import Generico.Genericos;
import Vistas.GUI_productos;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class ControlProductos {

    private GUI_productos dialoProductos;
    private Generico.GenericoConexion conexion;
    private String sintaxiSql;
    private GenericoCombo comboGene;

    public ControlProductos(JDialog objetoProductos) {
        dialoProductos = (GUI_productos) objetoProductos;
        conexion = new GenericoConexion();
    }

    public void cargarcmbimpuesto() {

        String sintaxSQL = null;
        dialoProductos.Comboimpuesto.removeAllItems();
        sintaxSQL = "SELECT id_impuesto, descri_impuesto FROM impuesto;";
        comboGene = new GenericoCombo(sintaxSQL);

        for (int i = 0; i < comboGene.getDatosTabla().size(); i++) {
            dialoProductos.Comboimpuesto.addItem(comboGene.getDatosTabla().get(i).getDesCombo());
        }
    }

    public void cargarcmEstado() {

        String sintaxSQL = null;
        dialoProductos.ComboEstado.removeAllItems();
        sintaxSQL = "SELECT id_estado,estado_descripcion FROM estado;";
        comboGene = new GenericoCombo(sintaxSQL);

        for (int i = 0; i < comboGene.getDatosTabla().size(); i++) {
            dialoProductos.ComboEstado.addItem(comboGene.getDatosTabla().get(i).getDesCombo());
        }
    }

    public enum opcionesDeRecuperar {
        recuperarProcedencia

    }

    public void initControles() {
        //setear cuadros de textos
        dialoProductos.txtCodigo.setEditable(false);
        dialoProductos.txtMarca.setEditable(false);

        dialoProductos.txtMarca.setText(null);
        dialoProductos.txtMarca.setEditable(false);

        dialoProductos.txtModelo.setText(null);
        dialoProductos.txtModelo.setEditable(false);

        dialoProductos.txtCantidad.setText(null);
        dialoProductos.txtCantidad.setEditable(false);

        dialoProductos.txtCantidad.setText(null);
        dialoProductos.txtCantidad.setEditable(false);

        dialoProductos.txtAño.setText(null);
        dialoProductos.txtAño.setEditable(false);

        dialoProductos.txtCodigoprocedencia.setText(null);
        dialoProductos.txtCodigoprocedencia.setEditable(false);

        dialoProductos.txtDescripcionprocedencia.setText(null);
        dialoProductos.txtDescripcionprocedencia.setEditable(false);

        dialoProductos.txtPreciocompra.setText(null);
        dialoProductos.txtPreciocompra.setEditable(false);

        dialoProductos.txtPrecioventa.setText(null);
        dialoProductos.txtPrecioventa.setEditable(false);

        dialoProductos.txtKm.setText(null);
        dialoProductos.txtKm.setEditable(false);

        dialoProductos.Comboimpuesto.setEnabled(false);
        dialoProductos.Comboimpuesto.setSelectedIndex(0);

        dialoProductos.ComboEstado.setEnabled(false);
        dialoProductos.ComboEstado.setSelectedIndex(0);

        dialoProductos.btGrabar.setEnabled(false);
        dialoProductos.btAgregar.requestFocus();
    }

    public void habilitaBotones() {
        dialoProductos.btAgregar.setEnabled(true);
        dialoProductos.btModificar.setEnabled(true);
        dialoProductos.btEliminar.setEnabled(true);
        dialoProductos.btGrabar.setEnabled(false);

    }

    public void desHabilitaBotones() {
        dialoProductos.btAgregar.setEnabled(false);
        dialoProductos.btModificar.setEnabled(false);
        dialoProductos.btEliminar.setEnabled(false);
    }

    public void alta() {
        sintaxiSql = null;
        sintaxiSql = "INSERT INTO productos\n"
                + "(produ_descripcion,productos_marca,produ_precio_comp,produ_precio_vent,productos_año,productos_km,impuesto_id_impuesto,procedencia_cod_procedencia,productos_cantidad,estado_id_estado) \n"
                + "VALUE ('" + Genericos.AntInyectSQL(dialoProductos.txtModelo.getText()) + "','" + Genericos.AntInyectSQL(dialoProductos.txtMarca.getText()) + "'," + Genericos.AntInyectSQL(dialoProductos.txtPreciocompra.getText()) + ", " + Genericos.AntInyectSQL(dialoProductos.txtPrecioventa.getText()) + " ," + Genericos.AntInyectSQL(dialoProductos.txtAño.getText()) + ", " + Genericos.AntInyectSQL(dialoProductos.txtKm.getText()) + " , " + Genericos.AntInyectSQL(dialoProductos.Comboimpuesto.getSelectedIndex()) + "  , " + Genericos.AntInyectSQL(dialoProductos.txtCodigoprocedencia.getText()) + "  , " + Genericos.AntInyectSQL(dialoProductos.txtCantidad.getText()) + ", " + Genericos.AntInyectSQL(dialoProductos.ComboEstado.getSelectedIndex()) + ");";
        System.out.println(sintaxiSql);
        conexion.ejecutarStatemenSet(sintaxiSql);
        //Falto el comit en el ejemplo aparantemte funciona pero cuando miras en la BD te das cuenta que no se ve reflejado la insercion... 
        conexion.comit();
        JOptionPane.showMessageDialog(null, "REGISTRO GUARDADO");

    }

    public void baja() {
        sintaxiSql = null;
        sintaxiSql = "DELETE FROM productos WHERE produ_codigo=" + dialoProductos.txtCodigo.getText().trim() + ";";
        conexion.ejecutarStatemenSet(sintaxiSql);
        //Falto el comit en el ejemplo aparantemte funciona pero cuando miras en la BD te das cuenta que no se ve reflejado la insercion... 
        conexion.comit();
        JOptionPane.showMessageDialog(null, "ELIMINACION EXITOSA");

    }

    public void modificacion() {
        sintaxiSql = null;
        sintaxiSql = "UPDATE productos  \n"
                + "SET \n"
                + "  produ_descripcion = '" + Genericos.AntInyectSQL(dialoProductos.txtModelo.getText()) + "',\n"
                + "  productos_marca = '" + Genericos.AntInyectSQL(dialoProductos.txtMarca.getText()) + "',\n"
                + "  produ_precio_comp = " + Genericos.AntInyectSQL(dialoProductos.txtPreciocompra.getText()) + ",\n"
                + "  produ_precio_vent = " + Genericos.AntInyectSQL(dialoProductos.txtPrecioventa.getText()) + ",\n"
                + "  productos_año = " + Genericos.AntInyectSQL(dialoProductos.txtAño.getText()) + ",\n"
                + "  productos_km =  " + Genericos.AntInyectSQL(dialoProductos.txtKm.getText()) + ",\n"
                + "  impuesto_id_impuesto = " + Genericos.AntInyectSQL(dialoProductos.Comboimpuesto.getSelectedIndex()) + ",\n"
                + "  procedencia_cod_procedencia = " + Genericos.AntInyectSQL(dialoProductos.txtCodigoprocedencia.getText()) + ",\n"
                + "  productos_cantidad = " + Genericos.AntInyectSQL(dialoProductos.txtCantidad.getText()) + ",\n"
                + "  estado_id_estado = " + Genericos.AntInyectSQL(dialoProductos.ComboEstado.getSelectedIndex()) + "\n"
                + "WHERE \n"
                + "  produ_codigo = " + dialoProductos.txtCodigo.getText().trim() + "";
        //Falto el comit en el ejemplo aparantemte funciona pero cuando miras en la BD te das cuenta que no se ve reflejado la insercion... 
        conexion.ejecutarStatemenSet(sintaxiSql);
        conexion.comit();
        JOptionPane.showMessageDialog(null, "REGISTRO MODIFICADO");
    }

    public boolean recuperarRegistro() {
        try {
            ResultSet cursor;
            sintaxiSql = null;
            sintaxiSql = "SELECT produ_codigo, productos_marca, produ_descripcion, produ_precio_comp, produ_precio_vent, impuesto.descri_impuesto, procedencia.procedencia_descrip , productos_año ,productos_km , productos_cantidad , estado_descripcion, productos.procedencia_cod_procedencia\n"
                    + "FROM productos, impuesto, procedencia , estado\n"
                    + "WHERE  impuesto.id_impuesto = impuesto_id_impuesto AND procedencia.cod_procedencia = procedencia_cod_procedencia AND estado.id_estado = estado_id_estado AND produ_codigo =" + dialoProductos.txtCodigo.getText().trim() + ";";
            cursor = conexion.ejecutarResultSet(sintaxiSql);
            if (cursor.next()) {
                
                dialoProductos.txtMarca.setText(cursor.getString("productos_marca"));
                dialoProductos.txtModelo.setText(cursor.getString("produ_descripcion"));
                dialoProductos.txtPreciocompra.setText(cursor.getString("produ_precio_comp"));
                dialoProductos.txtPrecioventa.setText(cursor.getString("produ_precio_vent"));
                dialoProductos.Comboimpuesto.setSelectedItem(cursor.getString("impuesto.descri_impuesto"));
                dialoProductos.txtCodigoprocedencia.setText(cursor.getString("productos.procedencia_cod_procedencia"));
                dialoProductos.txtDescripcionprocedencia.setText(cursor.getString("procedencia_descrip"));
                dialoProductos.txtAño.setText(cursor.getString("productos_año"));
                dialoProductos.txtKm.setText(cursor.getString("productos_km"));
                dialoProductos.txtCantidad.setText(cursor.getString("productos_cantidad"));
                dialoProductos.ComboEstado.setSelectedItem(cursor.getString("estado_descripcion"));
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControlProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void cargarGrilla() {
        DefaultTableModel modeloCargos = (DefaultTableModel) dialoProductos.GrillaProductos.getModel();
        modeloCargos.setRowCount(0);
        try {
            ResultSet cursor;
            sintaxiSql = null;
            // sintaxiSql = "SELECT produ_codigo, productos_marca, produ_descripcion, produ_precio_comp, produ_precio_vent, impuesto.descri_impuesto, procedencia.procedencia_descrip , productos_año ,productos_km , productos_cantidad  FROM productos, impuesto, procedencia WHERE  impuesto.id_impuesto = impuesto_id_impuesto AND procedencia.cod_procedencia = procedencia_cod_procedencia ORDER BY produ_codigo;";
            sintaxiSql = "SELECT produ_codigo, productos_marca, produ_descripcion, produ_precio_comp, produ_precio_vent, impuesto.descri_impuesto, procedencia.procedencia_descrip , productos_año ,productos_km , productos_cantidad , estado_descripcion\n"
                    + "FROM productos, impuesto, procedencia , estado\n"
                    + "WHERE  impuesto.id_impuesto = impuesto_id_impuesto AND procedencia.cod_procedencia = procedencia_cod_procedencia AND estado.id_estado = estado_id_estado\n"
                    + "ORDER BY produ_codigo;";
            cursor = conexion.ejecutarResultSet(sintaxiSql);
            while (cursor.next()) {
                modeloCargos.addRow(new Object[]{
                    cursor.getInt("produ_codigo"),
                    cursor.getString("productos_marca"),
                    cursor.getString("produ_descripcion"),
                    cursor.getString("produ_precio_comp"),
                    cursor.getString("produ_precio_vent"),
                    cursor.getString("impuesto.descri_impuesto"),
                    cursor.getString("procedencia.procedencia_descrip"),
                    cursor.getString("productos_año"),
                    cursor.getString("productos_km"),
                    cursor.getString("productos_cantidad"),
                    cursor.getString("estado_descripcion"),});
            }
            dialoProductos.GrillaProductos.setModel(modeloCargos);
        } catch (SQLException ex) {
            Logger.getLogger(ControlProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean recuperarRegistro(ControlProductos.opcionesDeRecuperar op, JTextField txt) {
        sintaxiSql = null;
        switch (op) {
            case recuperarProcedencia:
                sintaxiSql = "SELECT cod_procedencia, procedencia_descrip FROM procedencia WHERE cod_procedencia= " + dialoProductos.txtCodigoprocedencia.getText().trim() + "";
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
            Logger.getLogger(ControlProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public ResultSet generarResultSet(ControlProductos.opcionesDeRecuperar op) {
        ResultSet cursor;
        sintaxiSql = null;
        switch (op) {
            case recuperarProcedencia:
                sintaxiSql = "SELECT cod_procedencia, procedencia_descrip FROM procedencia";
                break;
        }
        return cursor = conexion.ejecutarResultSet(sintaxiSql);
    }

}
