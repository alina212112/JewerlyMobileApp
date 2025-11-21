package com.example.myapplication.data.models;

public class User {
    private Integer id;
    private String name;
    private String login;
    private String password;
    private String email;
    private String phone;
    private String created_at;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String name, String login, String phone, String email, String password) {
        this.name = name;
        this.login = login;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public User() {}

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getCreated_at() { return created_at; }
    public void setCreated_at(String created_at) { this.created_at = created_at; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", created_at='" + created_at + '\'' +
                '}';
    }

    public boolean isValidForLogin() {
        return email != null && !email.isEmpty() &&
                password != null && password.length() >= 6;
    }

    public boolean isValidForRegister() {
        return name != null && !name.isEmpty() &&
                login != null && !login.isEmpty() &&
                email != null && !email.isEmpty() &&
                phone != null && !phone.isEmpty() &&
                password != null && password.length() >= 8;
    }
}
