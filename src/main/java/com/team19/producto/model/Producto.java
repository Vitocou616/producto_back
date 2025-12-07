package com.team19.producto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

/**
 * Entidad que representa un Producto en el sistema
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long id;
    private String nombre;
    private Double precio;
    private String imagen;
    private String enlace;
    private String descripcion;
    private Integer stock;
    private Long creadoEn;
    private Long actualizadoEn;
    
    /**
     * Constructor simplificado para pruebas
     */
    public Producto(String nombre, Double precio, String imagen, String enlace) {
        this.nombre = nombre;
        this.precio = precio;
        this.imagen = imagen;
        this.enlace = enlace;
        this.stock = 100;
        this.creadoEn = System.currentTimeMillis();
        this.actualizadoEn = System.currentTimeMillis();
    }
}
