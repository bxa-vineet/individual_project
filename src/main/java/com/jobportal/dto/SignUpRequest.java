package com.jobportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {
    private String name;
    private String username;
    private String password;
    private Set<String> roles;
    private String email;

//    public String getUsername() { return username; }
//    public String getPassword() { return password; }
//    public String getRole() { return role; }
//
//    public void setUsername(String username) { this.username = username; }
//    public void setPassword(String password) { this.password = password; }
//    public void setRole(String role) { this.role = role; }
}
