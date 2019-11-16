
package Generico;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DELL
 */
public class ConstructorTabla extends DefaultTableModel {

    private boolean[] canEdit;
    private  ResultSet resultado;
    private ResultSetMetaData resultMetaDatos;
    private Object[] tamColum;

    public ConstructorTabla(ResultSet resulSet) {
        resultado = resulSet;
        initDatos();
    }


    
    private void initDatos() {
        try {
            
            // metaDatos = resultado.getMetaData();
            resultMetaDatos = resultado.getMetaData();
        } catch (SQLException e) {
            System.out.println("Error MetaData.. " + e);
        }
        listarColumnas();
        listarFilas();
    }

    private void listarColumnas() {
//        try {
//            for (int i = 1; i < resultMetaDatos.getColumnCount(); i++) {
//                System.out.println("getColumnLabel :"+resultMetaDatos.getColumnLabel(i));
//                System.out.println("getColumnName :"+resultMetaDatos.getColumnName(i));
//                System.out.println("getColumnTypeName :"+resultMetaDatos.getColumnTypeName(i));
//                System.out.println("getColumnDisplaySize :"+resultMetaDatos.getColumnDisplaySize(i));
//                System.out.println("getColumnType :"+resultMetaDatos.getColumnType(i));
//                System.out.println("getPrecision :"+resultMetaDatos.getPrecision(i));
//                 
//            }
//        } catch (Exception e) {
//            System.out.println(e);
//        }
// 
        try {
            Integer nroColum = resultMetaDatos.getColumnCount();
            columnasEditables(new boolean[nroColum]);
            Object[] etiquetas = new Object[nroColum];
            tamColum = new Object[nroColum];
            for (int i = 0; i < nroColum; i++) {
                tamColum[i] = resultMetaDatos.getPrecision(i + 1);
                etiquetas[i] = resultMetaDatos.getColumnLabel(i + 1);
                this.addColumn(etiquetas[i]);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConstructorTabla.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
       private void listarFilas() {
           this.setRowCount(0);
        try {
            while (resultado.next()) {
                Object[] datosFila = new Object[this.getColumnCount()];
                for (int i = 0; i < this.getColumnCount(); i++) {
                    datosFila[i] = resultado.getObject(i + 1);
                }
                this.addRow(datosFila);
            }
        } catch (SQLException eF) {
            System.out.println("Error MetaData.. " + eF);
        }
    }
    

    //Recupero el numero de columnas que contenga mi tabla 
    public int recuperarNroColumna() {
        return getColumnCount();
    }

    //Recupero el numero de filas que contenga mi tabla 
    public int recuperarNroFilas() {
        return getRowCount();
    }

    //remover la fila seleccionada 
    @Override
    public void removeRow(int row) {
        if (row >= 0) {
            super.removeRow(row);
            super.fireTableDataChanged();
        }
    }


    
    //Limpiar completamente los datos de la tabla 
    public void LimpiarTabla() {
        super.getDataVector().removeAllElements();
        super.fireTableDataChanged();
    }
    

    //Agregar una fila a la tabla  
    public void agregarFila(Object[] rowData) {
        super.addRow(rowData);
        super.fireTableDataChanged();
    }

    //Segun la cantidad de columnas recibir un true/false segun su edición 
    public void columnasEditables(boolean[] canEditRecibida) {
        canEdit = canEditRecibida;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit[columnIndex];
    }

}
