package com.team19.producto.config;

/**
 * Clase de configuración de la aplicación
 * Contiene constantes y configuraciones globales
 */
public class AppConfig {
    
    // Información de la aplicación
    public static final String APP_NAME = "Producto API";
    public static final String APP_VERSION = "1.0.0";
    public static final String APP_DESCRIPTION = "API REST para gestión de productos - Team 19";
    
    // Configuración de la API
    public static final String API_BASE_PATH = "/api";
    public static final String API_VERSION = "v1";
    
    // Configuración de CORS
    public static final String CORS_ALLOWED_ORIGINS = "*";
    public static final String[] CORS_ALLOWED_METHODS = {"GET", "POST", "PUT", "DELETE", "OPTIONS"};
    public static final String[] CORS_ALLOWED_HEADERS = {"*"};
    
    // Límites
    public static final int MAX_PAGE_SIZE = 100;
    public static final int DEFAULT_PAGE_SIZE = 10;
    
    // Mensajes
    public static final String MSG_PRODUCTO_NO_ENCONTRADO = "Producto no encontrado";
    public static final String MSG_ERROR_VALIDACION = "Error en la validación de datos";
    public static final String MSG_ERROR_INTERNO = "Error interno del servidor";
    
    private AppConfig() {
        // Clase de configuración, no instantiable
    }
}
