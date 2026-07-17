package com.banking.authservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Entity
@Data
@Builder
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
       private Long id;
       @Column(unique = true)
       private String fullName;
       @Column(unique = true)
       private String email;
       private String password;
       private String role;
       private LocalDateTime createdAt;
}
