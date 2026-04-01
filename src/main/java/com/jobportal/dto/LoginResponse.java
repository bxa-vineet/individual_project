package com.jobportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String token;
//    public LoginResponse(String token) {
//    }
//    public String getToken() {
//        return token;
//    }
//
////    public void setToken(String token) {
////        this.token = token;
////    }

}