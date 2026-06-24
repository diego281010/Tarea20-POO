package com.clinica.tarea20mysql.dao;

import com.clinica.tarea20mysql.conexion.Conexion;
import com.clinica.tarea20mysql.modelo.Participante;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class ParticipanteDAO {

    private final Connection conexion;

    public ParticipanteDAO() {
        conexion = Conexion
                .getInstancia()
                .getConexion();
    }

    /*
     * CREATE
     */
    public boolean insertar(Participante participante) {

        String sql = """
                INSERT INTO participantes
                (
                    cedula,
                    nombre,
                    apellido,
                    edad,
                    correo,
                    estado_civil,
                    jornada,
                    categoria,
                    observaciones
                )
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
                """;

        try {

            PreparedStatement ps =
                    conexion.prepareStatement(sql);

            ps.setString(1, participante.getCedula());
            ps.setString(2, participante.getNombre());
            ps.setString(3, participante.getApellido());
            ps.setInt(4, participante.getEdad());
            ps.setString(5, participante.getCorreo());
            ps.setString(6, participante.getEstadoCivil());
            ps.setString(7, participante.getJornada());
            ps.setString(8, participante.getCategoria());
            ps.setString(9, participante.getObservaciones());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error INSERT: "
                            + e.getMessage()
            );

            return false;
        }
    }

    /*
     * READ
     */
    public ObservableList<Participante> listarTodos() {

        ObservableList<Participante> lista =
                FXCollections.observableArrayList();

        String sql =
                "SELECT * FROM participantes";

        try {

            PreparedStatement ps =
                    conexion.prepareStatement(sql);

            ResultSet rs =
                    ps.executeQuery();

            while (rs.next()) {

                Participante participante =
                        new Participante();

                participante.setId(
                        rs.getInt("id"));

                participante.setCedula(
                        rs.getString("cedula"));

                participante.setNombre(
                        rs.getString("nombre"));

                participante.setApellido(
                        rs.getString("apellido"));

                participante.setEdad(
                        rs.getInt("edad"));

                participante.setCorreo(
                        rs.getString("correo"));

                participante.setEstadoCivil(
                        rs.getString("estado_civil"));

                participante.setJornada(
                        rs.getString("jornada"));

                participante.setCategoria(
                        rs.getString("categoria"));

                participante.setObservaciones(
                        rs.getString("observaciones"));

                lista.add(participante);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error SELECT: "
                            + e.getMessage()
            );
        }

        return lista;
    }

    /*
     * UPDATE
     */
    public boolean actualizar(Participante participante) {

        String sql = """
                UPDATE participantes
                SET
                    cedula=?,
                    nombre=?,
                    apellido=?,
                    edad=?,
                    correo=?,
                    estado_civil=?,
                    jornada=?,
                    categoria=?,
                    observaciones=?
                WHERE id=?
                """;

        try {

            PreparedStatement ps =
                    conexion.prepareStatement(sql);

            ps.setString(1,
                    participante.getCedula());

            ps.setString(2,
                    participante.getNombre());

            ps.setString(3,
                    participante.getApellido());

            ps.setInt(4,
                    participante.getEdad());

            ps.setString(5,
                    participante.getCorreo());

            ps.setString(6,
                    participante.getEstadoCivil());

            ps.setString(7,
                    participante.getJornada());

            ps.setString(8,
                    participante.getCategoria());

            ps.setString(9,
                    participante.getObservaciones());

            ps.setInt(10,
                    participante.getId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error UPDATE: "
                            + e.getMessage()
            );

            return false;
        }
    }

    /*
     * DELETE
     */
    public boolean eliminar(int id) {

        String sql =
                "DELETE FROM participantes WHERE id=?";

        try {

            PreparedStatement ps =
                    conexion.prepareStatement(sql);

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error DELETE: "
                            + e.getMessage()
            );

            return false;
        }
    }

    /*
     * VALIDAR CORREO REPETIDO
     */
    public boolean existeCorreo(String correo) {

        String sql =
                "SELECT COUNT(*) FROM participantes WHERE correo=?";

        try {

            PreparedStatement ps =
                    conexion.prepareStatement(sql);

            ps.setString(1, correo);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error validando correo: "
                            + e.getMessage()
            );
        }

        return false;
    }

    /*
     * VALIDAR CORREO REPETIDO
     * EXCLUYENDO EL ID ACTUAL
     */
    public boolean existeCorreo(
            String correo,
            int idActual
    ) {

        String sql = """
                SELECT COUNT(*)
                FROM participantes
                WHERE correo=?
                AND id<>?
                """;

        try {

            PreparedStatement ps =
                    conexion.prepareStatement(sql);

            ps.setString(1, correo);
            ps.setInt(2, idActual);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error validando correo: "
                            + e.getMessage()
            );
        }

        return false;
    }

    /*
     * BUSCAR POR ID
     */
    public Participante buscarPorId(int id) {

        String sql =
                "SELECT * FROM participantes WHERE id=?";

        try {

            PreparedStatement ps =
                    conexion.prepareStatement(sql);

            ps.setInt(1, id);

            ResultSet rs =
                    ps.executeQuery();

            if (rs.next()) {

                Participante participante =
                        new Participante();

                participante.setId(
                        rs.getInt("id"));

                participante.setCedula(
                        rs.getString("cedula"));

                participante.setNombre(
                        rs.getString("nombre"));

                participante.setApellido(
                        rs.getString("apellido"));

                participante.setEdad(
                        rs.getInt("edad"));

                participante.setCorreo(
                        rs.getString("correo"));

                participante.setEstadoCivil(
                        rs.getString("estado_civil"));

                participante.setJornada(
                        rs.getString("jornada"));

                participante.setCategoria(
                        rs.getString("categoria"));

                participante.setObservaciones(
                        rs.getString("observaciones"));

                return participante;
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error búsqueda: "
                            + e.getMessage()
            );
        }

        return null;
    }
}