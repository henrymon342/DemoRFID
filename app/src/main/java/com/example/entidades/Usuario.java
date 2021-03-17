package com.example.entidades;

public class Usuario {

    private Integer id;
    private String name;
    private String apellido;
    private String correo;
    private String password;

    public Usuario(Integer id, String name, String apellido, String correo, String password) {
        this.id = id;
        this.name = name;
        this.apellido = apellido;
        this.correo = correo;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
