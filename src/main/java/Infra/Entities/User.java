package Infra.Entities;

import lombok.Data;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String name;
    private boolean isActive = true;
    private boolean isAdmin = false;
}