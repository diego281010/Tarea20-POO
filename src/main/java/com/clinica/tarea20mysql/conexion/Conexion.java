package com.clinica.tarea20mysql.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static Conexion instancia;
    private Connection conexion;

    private final String URL = "jdbc:mysql://localhost:3306/natacion_db";
    private final String USER = "root";
    private final String PASSWORD = "root";

    private Conexion() {

        try {

            conexion = DriverManager.getConnection(URL, USER, PASSWORD);

            System.out.println("Conexión exitosa a MySQL");

        } catch (SQLException e) {

            System.out.println("Error de conexión: "+ e.getMessage());
        }
    }

    public static Conexion getInstancia() {

        if (instancia == null) {
            instancia = new Conexion();
        }
        return instancia;
    }

    public Connection getConexion() {
        return conexion;
    }
}