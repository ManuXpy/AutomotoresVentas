/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controles;

import Generico.GenericoCombo;
import Generico.GenericoConexion;
import Generico.Genericos;
import Generico.listarReportes;
import Vistas.GUI_factura;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
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
public class ControlFactura {

    private final GUI_factura dialoFactura;
    private final Generico.GenericoConexion conexion;
    private String sintaxiSql;
    private GenericoCombo comboGene;
    private final Generico.Genericos utiles = new Genericos();

    public ControlFactura(JDialog objetoFactura) {
        dialoFactura = (GUI_factura) objetoFactura;
        conexion = new GenericoConexion();

    }

    public enum opcionesDeRecuperar {
        recuperarEmpleado, recuperarCliente, recuperarProducto
    }

    public void initControles() {
        try {
            utiles.prepararDateField(dialoFactura.txtFecha);
            dialoFactura.txtFecha.setEditable(false);
            dialoFactura.txtFecha.setText(utiles.getFechaServidor());
            //cebecera 
            dialoFactura.txtCodigo.setEditable(false);
            dialoFactura.txtCodigo.setText(null);
            dialoFactura.txtCodigoEmpleado.setEditable(false);
            dialoFactura.txtDescripcionEmpleado.setEditable(false);
            dialoFactura.txtCodigoCliente.setEditable(false);
            dialoFactura.txtDescriCliente.setEditable(false);

            //detalle
            dialoFactura.txtCodigoProducto.setEditable(false);
            dialoFactura.txtProductoDescripcion.setEditable(false);
            dialoFactura.txtPrecio.setEditable(false);
            dialoFactura.txtimpuesto.setEditable(false);
            dialoFactura.txtEstado.setEditable(false);
            dialoFactura.txtCantidad.setEditable(false);

            dialoFactura.btGrabar.setEnabled(false);
            dialoFactura.btAgregar.requestFocus();
        } catch (ParseException ex) {
            Logger.getLogger(ControlFactura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void habilitaCabeceraTXT() {

        //cabecera
        dialoFactura.txtCodigoEmpleado.setEditable(true);
        dialoFactura.txtDescripcionEmpleado.setEditable(true);
        dialoFactura.txtCodigoCliente.setEditable(true);
        dialoFactura.txtDescriCliente.setEditable(true);

    }
    
     public void habilitaBotones() {
        dialoFactura.btAgregar.setEnabled(true);
        dialoFactura.btModificar.setEnabled(true);
        dialoFactura.btEliminar.setEnabled(true);
        dialoFactura.btGrabar.setEnabled(false);

    }

    public void limpiarTXT() {

        dialoFactura.txtCodigoEmpleado.setText(null);
        dialoFactura.txtDescripcionEmpleado.setText(null);
        dialoFactura.txtCodigoCliente.setText(null);
        dialoFactura.txtDescriCliente.setText(null);
    }

    public void habilitaDetalleTXT() {

        //detalle
        dialoFactura.txtCodigoProducto.setEditable(true);
        dialoFactura.txtCantidad.setEditable(true);

    }


    public void desHabilitaBotones() {
        dialoFactura.btAgregar.setEnabled(false);
        dialoFactura.btModificar.setEnabled(false);
        dialoFactura.btEliminar.setEnabled(false);
    }

    public void generarAutonumerico() {
        try {
            ResultSet cursor;
            sintaxiSql = null;
            sintaxiSql = "select coalesce(max(id_factura),0) + 1 as codigo from factura";
            cursor = conexion.ejecutarResultSet(sintaxiSql);
            if (cursor.next()) {

                dialoFactura.txtCodigo.setText(cursor.getObject("codigo").toString());
            }

        } catch (SQLException ex) {
            Logger.getLogger(ControlFactura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void InsertarPedido() {
        try {
            sintaxiSql = null;
            //datos de cabecera
            String fecha = utiles.getFechaFormateadaYYYYMMDD(dialoFactura.txtFecha.getText());

            sintaxiSql = "INSERT INTO factura(id_factura,fecha_factura,clientes_cod_cliente,empleados_emple_codigo,factura_tipo_id_factura_tipo) \n"
                    + "VALUE (" + dialoFactura.txtCodigo.getText().trim() + " , '" + fecha + "' ,  " + dialoFactura.txtCodigoCliente.getText().trim() + " , " + dialoFactura.txtCodigoEmpleado.getText().trim() + " , " + 1 + ");";

            System.out.println(sintaxiSql);
            conexion.ejecutarStatemenSet(sintaxiSql);
            conexion.comit();

            //datos del detalle
            for (int i = 0; i < dialoFactura.GrillaProductos.getRowCount(); i++) {
                String codigoProducto = dialoFactura.GrillaProductos.getValueAt(i, 0).toString();
                String cantProducto = dialoFactura.GrillaProductos.getValueAt(i, 5).toString();

                sintaxiSql = null;
                sintaxiSql = "INSERT INTO factura_detalle(productos_produ_codigo,factura_id_factura,cantidad, pagos_id_pago) \n"
                        + "VALUE (" + codigoProducto + " , " + dialoFactura.txtCodigo.getText().trim() + " , " + cantProducto + " ,  " + 1 + " );";

                System.out.println(sintaxiSql);
                conexion.ejecutarStatemenSet(sintaxiSql);
                conexion.comit();
            }
            HashMap parametro = new HashMap();
            parametro.put("facturaCodigo", Integer.parseInt(dialoFactura.txtCodigo.getText().trim()));

            String pNombreReporte = "src\\Informes\\factura.jrxml";
            String pNombreExport = "src\\Informes\\factura.pdf";
            listarReportes.imprimir(pNombreReporte, pNombreExport, parametro);
        } catch (ParseException ex) {
            Logger.getLogger(ControlProductos.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void modificacion() {

    }

    public void EliminarFila() {
        DefaultTableModel modelo = (DefaultTableModel) dialoFactura.GrillaProductos.getModel();

        modelo.removeRow(dialoFactura.GrillaProductos.getSelectedRow());
        modelo.fireTableDataChanged();

    }

    public void LimpiarTabla() {
        DefaultTableModel modelo = (DefaultTableModel) dialoFactura.GrillaProductos.getModel();
        if (modelo.getRowCount() > 0) {
            modelo.getDataVector().removeAllElements();
            modelo.fireTableDataChanged();
        }

    }

    public void limpiarDatosProductos() {
        dialoFactura.txtCodigoProducto.setText(null);
        dialoFactura.txtProductoDescripcion.setText(null);
        dialoFactura.txtPrecio.setText(null);
        dialoFactura.txtimpuesto.setText(null);
        dialoFactura.txtEstado.setText(null);
        dialoFactura.txtCantidad.setText(null);
        dialoFactura.txtCodigoProducto.requestFocus();

    }

    public boolean recuperarRegistro(ControlFactura.opcionesDeRecuperar op, JTextField txt) {
        sintaxiSql = null;
        switch (op) {
            case recuperarEmpleado:
                sintaxiSql = "SELECT empleados.emple_codigo, CONCAT(empleados.emple_nombres,\" \",empleados.emple_apellido) FROM empleados WHERE empleados.emple_codigo= " + dialoFactura.txtCodigoEmpleado.getText().trim() + "";
                break;
            case recuperarCliente:
                sintaxiSql = "SELECT clientes.cod_cliente, CONCAT( clientes.nombre_cliente, \" \", clientes.razonsocial_cliente)FROM clientes WHERE clientes.cod_cliente= " + dialoFactura.txtCodigoCliente.getText().trim() + "";
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

    public ResultSet generarResultSet(ControlFactura.opcionesDeRecuperar op) {
        ResultSet cursor;
        sintaxiSql = null;
        switch (op) {
            case recuperarEmpleado:
                sintaxiSql = "SELECT empleados.emple_codigo, CONCAT(empleados.emple_nombres,\" \",empleados.emple_apellido) FROM empleados";
                break;
            case recuperarCliente:
                sintaxiSql = "SELECT clientes.cod_cliente, CONCAT( clientes.nombre_cliente, \" \", clientes.razonsocial_cliente)FROM clientes";
                break;
            case recuperarProducto:
                sintaxiSql = "SELECT produ_codigo, CONCAT(productos_marca,\"  \", produ_descripcion), produ_precio_vent, impuesto.descri_impuesto,estado_descripcion\n"
                        + "FROM productos, impuesto, procedencia , estado\n"
                        + "WHERE  impuesto.id_impuesto = impuesto_id_impuesto AND procedencia.cod_procedencia = procedencia_cod_procedencia AND estado.id_estado = estado_id_estado";
                break;
        }
        return cursor = conexion.ejecutarResultSet(sintaxiSql);
    }

    public boolean recuperarRegistroProducto(ControlFactura.opcionesDeRecuperar op, JTextField txtCodigoProducto, JTextField txtProductoDescripcion, JTextField txtPrecio, JTextField txtimpuesto, JTextField txtEstado) {
        sintaxiSql = null;
        switch (op) {

            case recuperarProducto:
                sintaxiSql = "SELECT produ_codigo, CONCAT(productos_marca,\"  \", produ_descripcion), produ_precio_vent, impuesto.descri_impuesto,estado_descripcion\n"
                        + "FROM productos, impuesto, procedencia , estado\n"
                        + "WHERE  impuesto.id_impuesto = impuesto_id_impuesto AND procedencia.cod_procedencia = procedencia_cod_procedencia AND estado.id_estado = estado_id_estado AND produ_codigo= " + dialoFactura.txtCodigoProducto.getText().trim() + "";
                break;
            default:

                break;

        }

        try {
            ResultSet cursor;
            cursor = conexion.ejecutarResultSet(sintaxiSql);
            if (cursor.next()) {

                txtCodigoProducto.setText(cursor.getString(1));
                txtProductoDescripcion.setText(cursor.getString(2));
                txtPrecio.setText(cursor.getString(3));
                txtimpuesto.setText(cursor.getString(4));
                txtEstado.setText(cursor.getString(5));

                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControlProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void agregarFila() {
        String codProduIngresado = dialoFactura.txtCodigoProducto.getText().trim();
        DefaultTableModel modelo = (DefaultTableModel) dialoFactura.GrillaProductos.getModel();

        for (int i = 0; i < modelo.getRowCount(); i++) {
            String codRecuperado = modelo.getValueAt(i, 0).toString();
            if (codProduIngresado.equals(codRecuperado)) {
                int c = JOptionPane.showConfirmDialog(dialoFactura, "Producto ya Ingresado, desea aumentar la cantidad..???", "Aviso al Usuario", JOptionPane.YES_NO_OPTION);
                if (c == 0) {
                    Integer cantActual = Integer.parseInt(modelo.getValueAt(i, 5).toString());
                    Integer cantAgregar = Integer.parseInt(dialoFactura.txtCantidad.getText().trim());
                    Integer cantFinal = cantActual + cantAgregar;
                    modelo.setValueAt(cantFinal.toString(), i, 5);
                    return;
                }
                return;
            }
        }

        modelo.addRow(new Object[]{dialoFactura.txtCodigoProducto.getText().trim(),
            dialoFactura.txtProductoDescripcion.getText().trim(),
            dialoFactura.txtPrecio.getText().trim(),
            dialoFactura.txtimpuesto.getText().trim(),
            dialoFactura.txtEstado.getText().trim(),
            dialoFactura.txtCantidad.getText().trim()});
        dialoFactura.GrillaProductos.setModel(modelo);

    }

    public void RecupararPedido() {
        try {
            sintaxiSql = null;
            ResultSet cursor;
            String codFactura = dialoFactura.txtCodigo.getText().trim();
            DefaultTableModel modelo = (DefaultTableModel) dialoFactura.GrillaProductos.getModel();
            sintaxiSql = "SELECT\n"
                    + "factura.id_factura,\n"
                    + "factura.fecha_factura,\n"
                    + "factura.clientes_cod_cliente,\n"
                    + "factura.empleados_emple_codigo,\n"
                    + "factura_detalle.productos_produ_codigo,\n"
                    + "factura_detalle.factura_id_factura,\n"
                    + "factura_detalle.cantidad,\n"
                    + "productos.produ_precio_vent,\n"
                    + "impuesto.descri_impuesto,\n"
                    + "estado.estado_descripcion,\n"
                    + "CONCAT(productos.produ_descripcion,\"  \",productos.productos_marca),\n"
                    + "CONCAT(empleados.emple_nombres,\"  \",empleados.emple_apellido),\n"
                    + "CONCAT(clientes.nombre_cliente,\"  \",clientes.razonsocial_cliente)\n"
                    + "FROM\n"
                    + "factura_detalle,\n"
                    + "factura ,\n"
                    + "productos,\n"
                    + "empleados,\n"
                    + "clientes,\n"
                    + "impuesto,\n"
                    + "estado\n"
                    + "WHERE\n"
                    + "productos.produ_codigo=factura_detalle.productos_produ_codigo AND\n"
                    + "empleados.emple_codigo=factura.empleados_emple_codigo AND\n"
                    + "clientes.cod_cliente=factura.clientes_cod_cliente AND\n"
                    + "productos.impuesto_id_impuesto=impuesto.id_impuesto  AND\n"
                    + "productos.estado_id_estado=estado.id_estado AND\n"
                    + "factura.id_factura=factura_detalle.factura_id_factura AND\n"
                    + "factura_id_factura= " + codFactura + " ";
            cursor = conexion.ejecutarResultSet(sintaxiSql);
            while (cursor.next()) {
                dialoFactura.txtCodigo.setText(cursor.getObject("factura.id_factura").toString());
                dialoFactura.txtFecha.setText(utiles.retornaFechaddMMyyyy(cursor.getDate("factura.fecha_factura")));
                dialoFactura.txtCodigoEmpleado.setText(cursor.getObject("empleados_emple_codigo").toString());
                dialoFactura.txtDescripcionEmpleado.setText(cursor.getObject("CONCAT(empleados.emple_nombres,\"  \",empleados.emple_apellido)").toString());
                dialoFactura.txtCodigoCliente.setText(cursor.getObject("clientes_cod_cliente").toString());
                dialoFactura.txtDescriCliente.setText(cursor.getObject("CONCAT(clientes.nombre_cliente,\"  \",clientes.razonsocial_cliente)").toString());

                modelo.addRow(new Object[]{cursor.getObject("factura_detalle.productos_produ_codigo").toString(),
                    cursor.getObject("CONCAT(productos.produ_descripcion,\"  \",productos.productos_marca)").toString(),
                    cursor.getObject("produ_precio_vent").toString(),
                    cursor.getObject("descri_impuesto").toString(),
                    cursor.getObject("estado_descripcion").toString(),
                    cursor.getObject("factura_detalle.cantidad").toString()

                });

                dialoFactura.GrillaProductos.setModel(modelo);

            }

        } catch (SQLException ex) {
            Logger.getLogger(ControlFactura.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
