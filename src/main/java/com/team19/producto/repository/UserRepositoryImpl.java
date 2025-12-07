package com.team19.producto.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team19.producto.model.User;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepositoryImpl implements IUserRepository {
    private static final String USERS_FILE = "users.json";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AtomicLong idCounter = new AtomicLong(1);

    public UserRepositoryImpl() {
        inicializarArchivo();
    }

    private void inicializarArchivo() {
        File file = new File(USERS_FILE);
        if (!file.exists()) {
            List<User> usuariosDefault = Arrays.asList(
                    new User(1L, "Administrador", "admin@test.com", "admin123", "ADMIN", true),
                    new User(2L, "Usuario Prueba", "user@test.com", "user123", "USUARIO", true)
            );
            idCounter.set(3);
            guardarEnArchivo(usuariosDefault);
        } else {
            try {
                List<User> usuarios = leerDelArchivo();
                long maxId = usuarios.stream()
                        .map(User::getId)
                        .max(Long::compare)
                        .orElse(0L);
                idCounter.set(maxId + 1);
            } catch (IOException e) {
                System.out.println("Error al leer archivo de usuarios: " + e.getMessage());
            }
        }
    }

    private List<User> leerDelArchivo() throws IOException {
        File file = new File(USERS_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        return objectMapper.readValue(file, new TypeReference<List<User>>() {});
    }

    private void guardarEnArchivo(List<User> usuarios) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(USERS_FILE), usuarios);
        } catch (IOException e) {
            System.out.println("Error al guardar usuarios: " + e.getMessage());
        }
    }

    @Override
    public User guardar(User user) {
        try {
            List<User> usuarios = leerDelArchivo();
            
            if (user.getId() == null) {
                user.setId(idCounter.getAndIncrement());
            }

            // Si el usuario ya existe, actualizarlo
            usuarios.removeIf(u -> u.getId().equals(user.getId()));
            usuarios.add(user);

            guardarEnArchivo(usuarios);
            return user;
        } catch (IOException e) {
            System.out.println("Error al guardar usuario: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Optional<User> obtenerPorEmail(String email) {
        try {
            List<User> usuarios = leerDelArchivo();
            return usuarios.stream()
                    .filter(u -> u.getEmail().equalsIgnoreCase(email))
                    .findFirst();
        } catch (IOException e) {
            System.out.println("Error al obtener usuario por email: " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> obtenerPorId(Long id) {
        try {
            List<User> usuarios = leerDelArchivo();
            return usuarios.stream()
                    .filter(u -> u.getId().equals(id))
                    .findFirst();
        } catch (IOException e) {
            System.out.println("Error al obtener usuario por id: " + e.getMessage());
            return Optional.empty();
        }
    }
}
