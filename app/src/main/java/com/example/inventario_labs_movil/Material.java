package com.example.inventario_labs_movil;

public class Material {
    private String nombre;
    private String cantidad;
    private String laboratorio;
    public Material(String nombre,String cantidad, String laboratorio) {
        this.nombre=nombre;
        this.cantidad=cantidad;
        this.laboratorio=laboratorio;
    }

    public String getNombre()
    {
        return nombre;
    }

    public String getCantidad()
    {
        return cantidad;
    }

    public String getLaboratorio()
    {
        return laboratorio;
    }
}
