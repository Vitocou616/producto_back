package com.team19.producto.service;

import com.team19.producto.model.Producto;
import com.team19.producto.dto.ProductoDTO;
import com.team19.producto.repository.IProductoRepository;
import com.team19.producto.repository.ProductoRepositoryImpl;
import com.team19.producto.exception.ResourceNotFoundException;
import com.team19.producto.exception.BadRequestException;
import java.util.List;
import java.util.Optional;

/**
 * Implementación del servicio de Productos
 * Contiene la lógica de negocio
 */
public class ProductoServiceImpl implements IProductoService {
    
    private final IProductoRepository productoRepository;
    
    public ProductoServiceImpl() {
        this.productoRepository = new ProductoRepositoryImpl();
    }
    
    /**
     * Constructor para inyección de dependencias (testing)
     */
    public ProductoServiceImpl(IProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }
    
    @Override
    public List<Producto> obtenerTodos() {
        return productoRepository.findAll();
    }
    
    @Override
    public Optional<Producto> obtenerPorId(Long id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("El ID debe ser un número válido");
        }
        return productoRepository.findById(id);
    }
    
    @Override
    public List<Producto> buscarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new BadRequestException("El nombre de búsqueda no puede estar vacío");
        }
        return productoRepository.findByNombreContaining(nombre);
    }
    
    @Override
    public Producto crearProducto(ProductoDTO productoDTO) {
        validarProductoDTO(productoDTO);
        
        Producto producto = new Producto();
        producto.setNombre(productoDTO.getNombre());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setImagen(productoDTO.getImagen());
        producto.setEnlace(productoDTO.getEnlace());
        producto.setDescripcion(productoDTO.getDescripcion());
        producto.setStock(productoDTO.getStock() != null ? productoDTO.getStock() : 100);
        
        return productoRepository.save(producto);
    }
    
    @Override
    public Producto actualizarProducto(Long id, ProductoDTO productoDTO) {
        if (id == null || id <= 0) {
            throw new BadRequestException("El ID debe ser un número válido");
        }
        
        if (!productoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Producto no encontrado con ID: " + id);
        }
        
        Producto productoExistente = new Producto();
        productoExistente.setNombre(productoDTO.getNombre());
        productoExistente.setPrecio(productoDTO.getPrecio());
        productoExistente.setImagen(productoDTO.getImagen());
        productoExistente.setEnlace(productoDTO.getEnlace());
        productoExistente.setDescripcion(productoDTO.getDescripcion());
        productoExistente.setStock(productoDTO.getStock());
        
        return productoRepository.update(id, productoExistente);
    }
    
    @Override
    public boolean eliminarProducto(Long id) {
        if (id == null || id <= 0) {
            throw new BadRequestException("El ID debe ser un número válido");
        }
        
        if (!productoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Producto no encontrado con ID: " + id);
        }
        
        return productoRepository.deleteById(id);
    }
    
    @Override
    public long contarProductos() {
        return productoRepository.count();
    }
    
    /**
     * Valida los datos del DTO
     */
    private void validarProductoDTO(ProductoDTO productoDTO) {
        if (productoDTO.getNombre() == null || productoDTO.getNombre().trim().isEmpty()) {
            throw new BadRequestException("El nombre del producto es requerido");
        }
        if (productoDTO.getPrecio() == null || productoDTO.getPrecio() <= 0) {
            throw new BadRequestException("El precio debe ser un número válido mayor a 0");
        }
    }
}
