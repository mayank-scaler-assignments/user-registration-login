package com.example.bmsbookticket.controllers;

import com.example.bmsbookticket.dtos.*;
import com.example.bmsbookticket.dtos.ResponseStatus;
import com.example.bmsbookticket.models.User;
import com.example.bmsbookticket.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public SignupUserResponseDTO signupUser(@RequestBody SignupUserRequestDTO requestDTO) {
        SignupUserResponseDTO responseDTO = new SignupUserResponseDTO();
        try {
            User user = userService.signupUser(requestDTO.getName(), requestDTO.getEmail(), requestDTO.getPassword());
            responseDTO.setResponseStatus(ResponseStatus.SUCCESS);
            responseDTO.setEmail(user.getEmail());
            responseDTO.setName(user.getName());
        } catch (Exception e) {
            responseDTO.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDTO;
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto requestDto) {
        LoginResponseDto responseDto = new LoginResponseDto();
        try {
            boolean login = userService.login(requestDto.getEmail(), requestDto.getPassword());
            responseDto.setLoggedIn(login);
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (Exception e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
            responseDto.setLoggedIn(false);
        }
        return responseDto;
    }
}