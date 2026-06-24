package com.clinica.tarea20mysql.strategy;

public class ValidacionEdad implements ValidacionStrategy {

    @Override
    public boolean validar(String valor) {

        try {

            int edad = Integer.parseInt(valor);

            return edad > 5;

        } catch (Exception e) {

            return false;
        }
    }

    @Override
    public String getMensajeError() {

        return "La edad debe ser numérica y mayor a 5 años.";
    }
}