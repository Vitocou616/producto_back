package com.team19.producto.repository;

import com.team19.producto.model.User;
import java.util.Optional;

public interface IUserRepository {
    User guardar(User user);
    Optional<User> obtenerPorEmail(String email);
    Optional<User> obtenerPorId(Long id);
}
