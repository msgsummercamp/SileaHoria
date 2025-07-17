package com.example.data_demo.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class User {
    private int id;
    private String username;
    private String email;
    private String password;
    private String firstname;
    private String lastname;
}
