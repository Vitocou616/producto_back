package com.team19.producto.exception;

/**
 * Excepción personalizada para recursos no encontrados
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String mensaje) {
        super(mensaje);
    }
    
    public ResourceNotFoundException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
