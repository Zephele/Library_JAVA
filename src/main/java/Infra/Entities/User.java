package Infra.Entities;

import lombok.Data;

@Data // O Lombok continua a criar os Getters e Setters
public class User {
    private Long id;
    private String username;
    private String password;
    private String name;
    private boolean isActive = true;
    private boolean isAdmin = false;
}