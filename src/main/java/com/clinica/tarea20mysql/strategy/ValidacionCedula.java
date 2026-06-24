package com.clinica.tarea20mysql.strategy;

public class ValidacionCedula implements ValidacionStrategy {

    @Override
    public boolean validar(String valor) {

        return valor.matches("\\d+");
    }

    @Override
    public String getMensajeError() {

        return "La cédula solo debe contener números.";
    }
}