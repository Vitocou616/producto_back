package com.team19.producto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para crear o actualizar un Producto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {
    private String nombre;
    private Double precio;
    private String imagen;
    private String enlace;
    private String descripcion;
    private Integer stock;
}
