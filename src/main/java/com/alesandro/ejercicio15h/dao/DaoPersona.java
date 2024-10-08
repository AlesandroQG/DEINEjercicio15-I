package com.alesandro.ejercicio15h.dao;

import com.alesandro.ejercicio15h.db.DBConnect;
import com.alesandro.ejercicio15h.model.Persona;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DaoPersona {
    /**
     * Metodo que carga los datos de la tabla DNI y los devuelve para usarlos en un listado de personas
     *
     * @return listado de paises para cargar en un tableview
     */
    public static ObservableList<Persona> cargarListadoDNI() {
        DBConnect conexion;
        ObservableList<Persona> listadoDePersonas= FXCollections.observableArrayList();

        try{
            conexion = new DBConnect();

            String consulta = "SELECT id,nombre,apellidos,edad FROM PERSONA";
            PreparedStatement pstmt = conexion.getConnection().prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                int edad = rs.getInt("edad");
                Persona mp = new Persona(id,nombre,apellidos,edad);
                listadoDePersonas.add(mp);

            }
            rs.close();
            conexion.closeConexion();
        }catch (
                SQLException e) {
            // O lo trato aquí arriesgandome a qu fxml no este
                /*
                Alertas alertaError = new Alertas();
                alertaError.mostrarError("No he podido cargar el listado de paises");
                alertaError.mostrarError(e.getMessage());
                */
            // o lo trato aquí mostrandolo por consola
            System.out.println(e.getMessage());
        }
        return listadoDePersonas;
    }

    /**
     * Metodo que modifica los datos de un dni  en la BD
     *
     * @param p		Instancia de la persona con datos nuevos
     * @param pNueva Nuevo dni de la persona a modificar
     * @return			true/false
     */
    public static boolean modificarPais(Persona p, Persona pNueva) {
        DBConnect conexion;
        PreparedStatement pstmt;

        try {
            conexion = new DBConnect();
            // UPDATE `DNI`.`PAISES` SET `pais` = 'BulgariaK' WHERE (`pais` = 'Bulgaria');

            String consulta = "UPDATE PERSONA SET nombre = ?,apellidos = ?,edad = ? WHERE id = ?";
            pstmt = conexion.getConnection().prepareStatement(consulta);

            pstmt.setString(1, pNueva.getNombre());
            pstmt.setString(2, pNueva.getApellidos());
            pstmt.setInt(3, pNueva.getEdad());
            pstmt.setInt(4, p.getId());

            int filasAfectadas = pstmt.executeUpdate();

            System.out.println("Actualizada personas");
            //if (pstmt != null)
            pstmt.close();
            //if (conexion != null)
            conexion.closeConexion();
            return filasAfectadas > 0;
        } catch (SQLException e) {
               /* Alertas alertaError = new Alertas();
                alertaError.mostrarError("No he podido cargar el listado de paises");
                alertaError.mostrarError(e.getMessage());*/
            System.out.println(e.getMessage());
            return false;

        }

    }

    /**
     * Metodo que CREA un nuevo un dni en la BD
     *
     * @param persona		Instancia del modelo persona con datos nuevos
     * @return			id/-1
     */
    public  static int nuevoDNI(Persona persona) {
        DBConnect conexion;
        PreparedStatement pstmt;

        try {
            conexion = new DBConnect();
            // INSERT INTO `DNI`.`dni` (`dni`) VALUES ('el nuevo');

            String consulta = "INSERT INTO PERSON (nombre,apellidos,edad) VALUES (?,?,?) ";
            pstmt = conexion.getConnection().prepareStatement(consulta);

            pstmt.setString(1, persona.getNombre());
            pstmt.setString(2, persona.getApellidos());
            pstmt.setInt(3, persona.getEdad());

            int filasAfectadas = pstmt.executeUpdate();
            //if (pstmt != null)
            pstmt.close();
            //if (conexion != null)
            conexion.closeConexion();
            System.out.println("Nueva entrada en  persona");
            if (filasAfectadas > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    return id;
                }
            }
            return -1;
        } catch (SQLException e) {
               /* Alertas alertaError = new Alertas();
                alertaError.mostrarError("No he podido cargar el listado de dnis");
                alertaError.mostrarError(e.getMessage());*/
            System.out.println(e.getMessage());
            return -1;

        }

    }

    /**
     * Elimina una persona en función del modelo Persona que le hayamos pasado
     *
     * @param personaAEliminar Persona a eliminar
     * @return a boolean
     */
    public  static boolean eliminarPersona (Persona personaAEliminar){

        DBConnect conexion;
        PreparedStatement pstmt;
        try {
            conexion = new DBConnect();
            //DELETE FROM `DNI`.`dni` WHERE (`dni` = 'asdasd');
            String consulta = "DELETE FROM PERSONA WHERE (id = ?)";
            pstmt = conexion.getConnection().prepareStatement(consulta);
            pstmt.setInt(1, personaAEliminar.getId());
            int filasAfectadas = pstmt.executeUpdate();
            pstmt.close();
            conexion.closeConexion();
            System.out.println("Eliminado con éxito");
            return filasAfectadas > 0;

        } catch (SQLException e) {
                /*
                Alertas alertaError = new Alertas();
                alertaError.mostrarError("No he podido borrar ese registro");
                alertaError.mostrarError(e.getMessage());*/
            System.out.println(e.getMessage());
            return false;
        }
    }

}
