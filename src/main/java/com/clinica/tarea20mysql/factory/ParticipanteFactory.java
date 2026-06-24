package com.clinica.tarea20mysql.factory;

import com.clinica.tarea20mysql.modelo.Participante;

public class ParticipanteFactory {

    private ParticipanteFactory() {
    }

    public static Participante crearParticipante(
            String cedula,
            String nombre,
            String apellido,
            int edad,
            String correo,
            String estadoCivil,
            String jornada,
            String categoria,
            String observaciones
    ) {

        Participante participante = new Participante();

        participante.setCedula(cedula);
        participante.setNombre(nombre);
        participante.setApellido(apellido);
        participante.setEdad(edad);
        participante.setCorreo(correo);
        participante.setEstadoCivil(estadoCivil);
        participante.setJornada(jornada);
        participante.setCategoria(categoria);
        participante.setObservaciones(observaciones);

        return participante;
    }
}