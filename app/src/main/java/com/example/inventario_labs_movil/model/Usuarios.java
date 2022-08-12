package com.example.inventario_labs_movil.model;

public class Usuarios {

    private String contraseña, correo, privilegio;

    public Usuarios() {

    }

    public Usuarios(String contraseña, String correo, String privilegio) {
        this.contraseña = contraseña;
        this.correo = correo;
        this.privilegio = privilegio;
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
