package com.clinica.tarea20mysql.strategy;

public interface ValidacionStrategy {

    boolean validar(String valor);

    String getMensajeError();
}