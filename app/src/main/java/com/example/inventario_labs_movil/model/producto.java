package com.example.inventario_labs_movil.model;

public class producto {
    String Nombre, Id, Cantidad;
    public producto(){}
    public producto(String nombre, String id, String cantidad) {
        Nombre = nombre;
        Id = id;
        Cantidad = cantidad;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getCantidad() {
        return Cantidad;
    }

    public void setCantidad(String cantidad) {
        Cantidad = cantidad;
    }
}
