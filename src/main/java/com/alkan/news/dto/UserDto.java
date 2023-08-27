package com.alkan.news.dto;

import java.io.Serializable;

public class UserDto implements Serializable {

    public int id;
    public String name;
    public String surname;
    public String email;
    public String password;
    public boolean active;

    public UserDto() {
    }
}
