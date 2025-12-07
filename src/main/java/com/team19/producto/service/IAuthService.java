package com.team19.producto.service;

import com.team19.producto.dto.LoginDTO;
import com.team19.producto.dto.RegisterDTO;

public interface IAuthService {
    String login(LoginDTO loginDTO) throws Exception;
    String register(RegisterDTO registerDTO) throws Exception;
}
