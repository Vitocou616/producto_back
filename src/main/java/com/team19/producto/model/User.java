package com.team19.producto.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String nombre;
    private String email;
    private String password;
    private String rol; // "USUARIO" o "ADMIN"
    private Boolean activo;
}
