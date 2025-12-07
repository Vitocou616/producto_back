package com.team19.producto.controller;

import com.team19.producto.dto.ApiResponse;
import com.team19.producto.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = "*")
public class ProductoController {
    
    // Simulamos una lista de productos en memoria
    private static List<Map<String, Object>> productos = new ArrayList<>();
    
    static {
        Map<String, Object> p1 = new HashMap<>();
        p1.put("id", 1);
        p1.put("nombre", "New Mutants Combate el Futuro 3 de 3");
        p1.put("precio", 5990);
        p1.put("imagen", "/src/images/covers/0006e93f62963735cf75b324bbccbf00e976ed2325127889.jpg");
        p1.put("enlace", "/producto/1");
        productos.add(p1);

        Map<String, Object> p2 = new HashMap<>();
        p2.put("id", 2);
        p2.put("nombre", "Patrulla X Especie en Peligro 13");
        p2.put("precio", 8990);
        p2.put("imagen", "/src/images/covers/0068adf75be74e30491346d250a383c12cfa8fbd27623174.jpg");
        p2.put("enlace", "/producto/2");
        productos.add(p2);

        Map<String, Object> p3 = new HashMap<>();
        p3.put("id", 3);
        p3.put("nombre", "Superior Ironman");
        p3.put("precio", 15990);
        p3.put("imagen", "/src/images/covers/0210a8ecdb91c6247d5f0b19bfbf37811197e7827696592.jpg");
        p3.put("enlace", "/producto/3");
        productos.add(p3);
    }

    @Autowired
    private JwtUtil jwtUtil;

    private Long nextId = 3L;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> obtenerTodos() {
        return ResponseEntity.ok(ApiResponse.success(productos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> obtenerPorId(@PathVariable Long id) {
        Optional<Map<String, Object>> producto = productos.stream()
                .filter(p -> ((Integer) p.get("id")).equals(id.intValue()))
                .findFirst();

        if (producto.isPresent()) {
            return ResponseEntity.ok(ApiResponse.success(producto.get()));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("Producto no encontrado"));
    }

    @GetMapping("/buscar")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> buscar(@RequestParam(name = "q", defaultValue = "") String query) {
        List<Map<String, Object>> resultados = productos.stream()
                .filter(p -> ((String) p.get("titulo")).toLowerCase().contains(query.toLowerCase())
                        || ((String) p.get("autor")).toLowerCase().contains(query.toLowerCase()))
                .toList();
        return ResponseEntity.ok(ApiResponse.success(resultados));
    }

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<String>> health() {
        return ResponseEntity.ok(ApiResponse.success("API running correctly"));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> crear(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestBody Map<String, Object> productoDTO) {
        
        if (!validarAdminToken(authHeader)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ApiResponse.error("No tienes permisos para crear productos"));
        }

        Map<String, Object> nuevoProducto = new HashMap<>(productoDTO);
        nuevoProducto.put("id", nextId++);
        productos.add(nuevoProducto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(nuevoProducto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> actualizar(
            @PathVariable Long id,
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @RequestBody Map<String, Object> productoDTO) {
        
        if (!validarAdminToken(authHeader)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ApiResponse.error("No tienes permisos para actualizar productos"));
        }

        Optional<Map<String, Object>> producto = productos.stream()
                .filter(p -> ((Integer) p.get("id")).equals(id.intValue()))
                .findFirst();

        if (producto.isPresent()) {
            Map<String, Object> p = producto.get();
            productoDTO.forEach((key, value) -> {
                if (!"id".equals(key)) {
                    p.put(key, value);
                }
            });
            return ResponseEntity.ok(ApiResponse.success(p));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("Producto no encontrado"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> eliminar(
            @PathVariable Long id,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {
        
        if (!validarAdminToken(authHeader)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(ApiResponse.error("No tienes permisos para eliminar productos"));
        }

        boolean removed = productos.removeIf(p -> ((Integer) p.get("id")).equals(id.intValue()));

        if (removed) {
            return ResponseEntity.ok(ApiResponse.success("Producto eliminado correctamente"));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.error("Producto no encontrado"));
    }

    private boolean validarAdminToken(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return false;
        }

        String token = authHeader.substring(7);
        if (!jwtUtil.validarToken(token)) {
            return false;
        }

        String rol = jwtUtil.obtenerRolDelToken(token);
        return "ADMIN".equals(rol);
    }
}
