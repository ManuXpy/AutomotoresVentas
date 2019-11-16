/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Generico;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author emanu
 */
public class GenericoCombo {

    private ArrayList<registrosTabla> datos;
    private GenericoConexion conec = new GenericoConexion();
    private ResultSet rs;

    public GenericoCombo(String sql) {
        try {
            datos = new ArrayList<>();
            datos.add(new registrosTabla(0, "<No Seleccion>"));
            rs = conec.ejecutarResultSet(sql);
            while (rs.next()) {
                datos.add(new registrosTabla(rs.getInt(1), rs.getString(2)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(GenericoCombo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<registrosTabla> getDatosTabla() {
        return datos;
    }

    public Integer getCodigoActual(int indice) {
        if (indice != -1) {
            return datos.get(indice).getCodCombo();
        }
        return 0;
    }

    public class registrosTabla {

        private final Integer codCombo;
        private final String desCombo;

        public registrosTabla(Integer codCombo, String desCombo) {
            this.codCombo = codCombo;
            this.desCombo = desCombo;
        }

        public Integer getCodCombo() {
            return codCombo;
        }

        public String getDesCombo() {
            return desCombo;
        }
    }

}
