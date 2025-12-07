package com.team19.producto.service;

import com.team19.producto.model.Producto;
import com.team19.producto.dto.ProductoDTO;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz del servicio de Productos
 * Define la lógica de negocio
 */
public interface IProductoService {
    
    /**
     * Obtiene todos los productos
     */
    List<Producto> obtenerTodos();
    
    /**
     * Obtiene un producto por ID
     */
    Optional<Producto> obtenerPorId(Long id);
    
    /**
     * Busca productos por nombre
     */
    List<Producto> buscarPorNombre(String nombre);
    
    /**
     * Crea un nuevo producto
     */
    Producto crearProducto(ProductoDTO productoDTO);
    
    /**
     * Actualiza un producto existente
     */
    Producto actualizarProducto(Long id, ProductoDTO productoDTO);
    
    /**
     * Elimina un producto
     */
    boolean eliminarProducto(Long id);
    
    /**
     * Obtiene el total de productos
     */
    long contarProductos();
}
