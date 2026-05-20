package com.example.financial_favorite.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "`User`")
public class User {
    @Id
    @Column(name = "UserID", length = 20)
    private String userID;

    @Column(name = "UserName", length = 50, nullable = false)
    private String userName;

    @Column(name = "Email", length = 100, nullable = false)
    private String email;

    @Column(name = "Account", length = 20, nullable = false)
    private String account;
}
