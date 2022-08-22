package com.example.inventario_labs_movil.model;

public class Usuarios {

    private String nombre, contraseña, correo, privilegio;

    public Usuarios() {

    }

    public Usuarios(String nombre, String contraseña, String correo, String privilegio) {
        this.contraseña = contraseña;
        this.correo = correo;
        this.privilegio = privilegio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setPrivilegio(String privilegio) {
        this.privilegio = privilegio;
    }

    public String getContraseña() {
        return contraseña;
    }

    public String getCorreo() {
        return correo;
    }

    public String getPrivilegio() {
        return privilegio;
    }
}
