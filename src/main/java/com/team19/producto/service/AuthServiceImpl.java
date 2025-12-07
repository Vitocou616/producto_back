package com.team19.producto.service;

import com.team19.producto.dto.LoginDTO;
import com.team19.producto.dto.RegisterDTO;
import com.team19.producto.model.User;
import com.team19.producto.repository.IUserRepository;
import com.team19.producto.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements IAuthService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public String login(LoginDTO loginDTO) throws Exception {
        if (loginDTO.getEmail() == null || loginDTO.getPassword() == null) {
            throw new Exception("Email y contraseña son requeridos");
        }

        User user = userRepository.obtenerPorEmail(loginDTO.getEmail())
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        if (!user.getPassword().equals(loginDTO.getPassword())) {
            throw new Exception("Contraseña incorrecta");
        }

        if (!user.getActivo()) {
            throw new Exception("Usuario inactivo");
        }

        return jwtUtil.generarToken(user.getEmail(), user.getRol());
    }

    @Override
    public String register(RegisterDTO registerDTO) throws Exception {
        if (registerDTO.getNombre() == null || registerDTO.getEmail() == null || registerDTO.getPassword() == null) {
            throw new Exception("Nombre, email y contraseña son requeridos");
        }

        if (registerDTO.getPassword().length() < 6) {
            throw new Exception("La contraseña debe tener al menos 6 caracteres");
        }

        if (userRepository.obtenerPorEmail(registerDTO.getEmail()).isPresent()) {
            throw new Exception("Ya existe un usuario con ese email");
        }

        User newUser = new User();
        newUser.setNombre(registerDTO.getNombre());
        newUser.setEmail(registerDTO.getEmail());
        newUser.setPassword(registerDTO.getPassword());
        newUser.setRol("USUARIO");
        newUser.setActivo(true);

        User savedUser = userRepository.guardar(newUser);

        return jwtUtil.generarToken(savedUser.getEmail(), savedUser.getRol());
    }
}
