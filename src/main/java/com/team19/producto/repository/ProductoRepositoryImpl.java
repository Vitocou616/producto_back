package com.team19.producto.repository;

import com.team19.producto.model.Producto;
import java.io.*;
import java.util.*;
import java.nio.file.*;

/**
 * Implementación del repositorio usando persistencia en JSON
 */
public class ProductoRepositoryImpl implements IProductoRepository {
    
    private static final String DATA_FILE = "productos.json";
    private static final long serialVersionUID = 1L;
    private List<Producto> productos = new ArrayList<>();
    
    public ProductoRepositoryImpl() {
        cargarProductos();
    }
    
    /**
     * Carga los productos desde archivo JSON
     */
    private void cargarProductos() {
        try {
            if (Files.exists(Paths.get(DATA_FILE))) {
                String contenido = new String(Files.readAllBytes(Paths.get(DATA_FILE)));
                productos = parsearJSON(contenido);
            } else {
                productos = inicializarProductosPorDefecto();
                guardarProductos();
            }
        } catch (IOException e) {
            System.err.println("Error cargando productos: " + e.getMessage());
            productos = inicializarProductosPorDefecto();
        }
    }
    
    /**
     * Inicializa productos por defecto
     */
    private List<Producto> inicializarProductosPorDefecto() {
        List<Producto> porDefecto = new ArrayList<>();
        porDefecto.add(new Producto(1L, "New Mutants Combate el Futuro 3 de 3", 5990.0, 
            "/src/images/covers/0006e93f62963735cf75b324bbccbf00e976ed2325127889.jpg", "/producto/1", 
            "Comic New Mutants", 100, System.currentTimeMillis(), System.currentTimeMillis()));
        porDefecto.add(new Producto(2L, "Patrulla X Especie en Peligro 13", 8990.0,
            "/src/images/covers/0068adf75be74e30491346d250a383c12cfa8fbd27623174.jpg", "/producto/2",
            "Comic Patrulla X", 100, System.currentTimeMillis(), System.currentTimeMillis()));
        porDefecto.add(new Producto(3L, "Superior Ironman", 15990.0,
            "/src/images/covers/0210a8ecdb91c6247d5f0b19bfbf37811197e7827696592.jpg", "/producto/3",
            "Comic Ironman", 100, System.currentTimeMillis(), System.currentTimeMillis()));
        return porDefecto;
    }
    
    @Override
    public List<Producto> findAll() {
        return new ArrayList<>(productos);
    }
    
    @Override
    public Optional<Producto> findById(Long id) {
        return productos.stream()
            .filter(p -> p.getId().equals(id))
            .findFirst();
    }
    
    @Override
    public List<Producto> findByNombreContaining(String nombre) {
        return productos.stream()
            .filter(p -> p.getNombre().toLowerCase().contains(nombre.toLowerCase()))
            .toList();
    }
    
    @Override
    public Producto save(Producto producto) {
        Long nuevoId = productos.stream()
            .mapToLong(p -> p.getId() != null ? p.getId() : 0)
            .max()
            .orElse(0L) + 1;
        
        producto.setId(nuevoId);
        producto.setCreadoEn(System.currentTimeMillis());
        producto.setActualizadoEn(System.currentTimeMillis());
        
        productos.add(producto);
        guardarProductos();
        
        return producto;
    }
    
    @Override
    public Producto update(Long id, Producto productoActualizado) {
        Optional<Producto> productoExistente = findById(id);
        
        if (productoExistente.isEmpty()) {
            throw new RuntimeException("Producto no encontrado con ID: " + id);
        }
        
        Producto p = productoExistente.get();
        
        if (productoActualizado.getNombre() != null) {
            p.setNombre(productoActualizado.getNombre());
        }
        if (productoActualizado.getPrecio() != null) {
            p.setPrecio(productoActualizado.getPrecio());
        }
        if (productoActualizado.getImagen() != null) {
            p.setImagen(productoActualizado.getImagen());
        }
        if (productoActualizado.getEnlace() != null) {
            p.setEnlace(productoActualizado.getEnlace());
        }
        if (productoActualizado.getDescripcion() != null) {
            p.setDescripcion(productoActualizado.getDescripcion());
        }
        if (productoActualizado.getStock() != null) {
            p.setStock(productoActualizado.getStock());
        }
        
        p.setActualizadoEn(System.currentTimeMillis());
        
        guardarProductos();
        return p;
    }
    
    @Override
    public boolean deleteById(Long id) {
        boolean eliminado = productos.removeIf(p -> p.getId().equals(id));
        if (eliminado) {
            guardarProductos();
        }
        return eliminado;
    }
    
    @Override
    public boolean existsById(Long id) {
        return productos.stream().anyMatch(p -> p.getId().equals(id));
    }
    
    @Override
    public long count() {
        return productos.size();
    }
    
    /**
     * Guarda los productos en archivo JSON
     */
    private void guardarProductos() {
        try {
            String json = convertirAJSON(productos);
            Files.write(Paths.get(DATA_FILE), json.getBytes());
        } catch (IOException e) {
            System.err.println("Error guardando productos: " + e.getMessage());
        }
    }
    
    /**
     * Convierte lista de productos a JSON (formato simple)
     */
    private String convertirAJSON(List<Producto> productos) {
        StringBuilder json = new StringBuilder("[\n");
        for (int i = 0; i < productos.size(); i++) {
            Producto p = productos.get(i);
            json.append("  {\n");
            json.append("    \"id\": ").append(p.getId()).append(",\n");
            json.append("    \"nombre\": \"").append(escaparJSON(p.getNombre())).append("\",\n");
            json.append("    \"precio\": ").append(p.getPrecio()).append(",\n");
            json.append("    \"imagen\": \"").append(escaparJSON(p.getImagen())).append("\",\n");
            json.append("    \"enlace\": \"").append(escaparJSON(p.getEnlace())).append("\",\n");
            json.append("    \"descripcion\": \"").append(escaparJSON(p.getDescripcion())).append("\",\n");
            json.append("    \"stock\": ").append(p.getStock()).append(",\n");
            json.append("    \"creadoEn\": ").append(p.getCreadoEn()).append(",\n");
            json.append("    \"actualizadoEn\": ").append(p.getActualizadoEn()).append("\n");
            json.append("  }");
            if (i < productos.size() - 1) json.append(",");
            json.append("\n");
        }
        json.append("]");
        return json.toString();
    }
    
    /**
     * Parsea JSON a lista de productos (formato simple)
     */
    private List<Producto> parsearJSON(String json) {
        List<Producto> lista = new ArrayList<>();
        // Implementación simplificada - en producción usar Jackson o Gson
        try {
            json = json.trim();
            if (json.startsWith("[") && json.endsWith("]")) {
                json = json.substring(1, json.length() - 1);
                String[] objetos = json.split("\\}\\s*,\\s*\\{");
                
                for (String obj : objetos) {
                    obj = obj.replace("{", "").replace("}", "").trim();
                    Long id = extraerLong(obj, "\"id\"");
                    String nombre = extraerString(obj, "\"nombre\"");
                    Double precio = extraerDouble(obj, "\"precio\"");
                    String imagen = extraerString(obj, "\"imagen\"");
                    String enlace = extraerString(obj, "\"enlace\"");
                    String descripcion = extraerString(obj, "\"descripcion\"");
                    Integer stock = extraerInteger(obj, "\"stock\"");
                    Long creadoEn = extraerLong(obj, "\"creadoEn\"");
                    Long actualizadoEn = extraerLong(obj, "\"actualizadoEn\"");
                    
                    if (id != null) {
                        lista.add(new Producto(id, nombre, precio, imagen, enlace, descripcion, stock, creadoEn, actualizadoEn));
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error parseando JSON: " + e.getMessage());
        }
        return lista;
    }
    
    private Long extraerLong(String json, String clave) {
        try {
            int inicio = json.indexOf(clave) + clave.length() + 1;
            int fin = json.indexOf(",", inicio);
            if (fin == -1) fin = json.indexOf("}", inicio);
            String valor = json.substring(inicio, fin).trim();
            return Long.parseLong(valor);
        } catch (Exception e) {
            return null;
        }
    }
    
    private Double extraerDouble(String json, String clave) {
        try {
            int inicio = json.indexOf(clave) + clave.length() + 1;
            int fin = json.indexOf(",", inicio);
            if (fin == -1) fin = json.indexOf("}", inicio);
            String valor = json.substring(inicio, fin).trim();
            return Double.parseDouble(valor);
        } catch (Exception e) {
            return null;
        }
    }
    
    private Integer extraerInteger(String json, String clave) {
        try {
            int inicio = json.indexOf(clave) + clave.length() + 1;
            int fin = json.indexOf(",", inicio);
            if (fin == -1) fin = json.indexOf("}", inicio);
            String valor = json.substring(inicio, fin).trim();
            return Integer.parseInt(valor);
        } catch (Exception e) {
            return null;
        }
    }
    
    private String extraerString(String json, String clave) {
        try {
            int inicio = json.indexOf(clave) + clave.length() + 2;
            int fin = json.indexOf("\"", inicio);
            return json.substring(inicio, fin);
        } catch (Exception e) {
            return "";
        }
    }
    
    private String escaparJSON(String texto) {
        if (texto == null) return "";
        return texto.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "\\r");
    }
}
