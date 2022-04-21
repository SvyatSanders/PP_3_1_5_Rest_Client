package ru.sviatoslav.restclient.entity;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private int id;
    private String name;
    private String lastName;
    private int age;

}
