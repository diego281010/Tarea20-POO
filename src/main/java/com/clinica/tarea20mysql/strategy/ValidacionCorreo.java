package com.clinica.tarea20mysql.strategy;

public class ValidacionCorreo implements ValidacionStrategy {

    @Override
    public boolean validar(String valor) {

        return valor != null
                && valor.contains("@")
                && valor.contains(".");
    }

    @Override
    public String getMensajeError() {

        return "El correo debe contener @ y dominio válido.";
    }
}