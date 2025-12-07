package com.team19.producto.controller;

import com.team19.producto.dto.ApiResponse;
import com.team19.producto.dto.LoginDTO;
import com.team19.producto.dto.RegisterDTO;
import com.team19.producto.service.IAuthService;
import com.team19.producto.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private IAuthService authService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestBody LoginDTO loginDTO) {
        try {
            String token = authService.login(loginDTO);
            return ResponseEntity.ok(ApiResponse.success(token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@RequestBody RegisterDTO registerDTO) {
        try {
            String token = authService.register(registerDTO);
            return ResponseEntity.ok(ApiResponse.success(token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<ApiResponse<Object>> validateToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.error("Token no proporcionado o inválido"));
            }

            String token = authHeader.substring(7);
            if (!jwtUtil.validarToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.error("Token inválido o expirado"));
            }

            String email = jwtUtil.obtenerEmailDelToken(token);
            String rol = jwtUtil.obtenerRolDelToken(token);

            return ResponseEntity.ok(ApiResponse.success(
                    java.util.Map.of("email", email, "rol", rol)
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.error("Error validando token: " + e.getMessage()));
        }
    }
}
