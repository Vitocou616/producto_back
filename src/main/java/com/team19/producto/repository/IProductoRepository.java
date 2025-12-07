package com.team19.producto.repository;

import com.team19.producto.model.Producto;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz del repositorio para operaciones con Productos
 * Define contrato CRUD
 */
public interface IProductoRepository {
    
    /**
     * Obtiene todos los productos
     */
    List<Producto> findAll();
    
    /**
     * Obtiene un producto por ID
     */
    Optional<Producto> findById(Long id);
    
    /**
     * Busca productos por nombre (búsqueda parcial)
     */
    List<Producto> findByNombreContaining(String nombre);
    
    /**
     * Guarda un nuevo producto
     */
    Producto save(Producto producto);
    
    /**
     * Actualiza un producto existente
     */
    Producto update(Long id, Producto producto);
    
    /**
     * Elimina un producto por ID
     */
    boolean deleteById(Long id);
    
    /**
     * Verifica si existe un producto con ese ID
     */
    boolean existsById(Long id);
    
    /**
     * Obtiene el número de productos
     */
    long count();
}
