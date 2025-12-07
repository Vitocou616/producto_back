package com.team19.producto.exception;

/**
 * Excepción personalizada para validaciones
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String mensaje) {
        super(mensaje);
    }
    
    public BadRequestException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
