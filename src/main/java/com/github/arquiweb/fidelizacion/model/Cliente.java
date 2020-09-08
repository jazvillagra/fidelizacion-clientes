package com.github.arquiweb.fidelizacion.model;

import javax.persistence.*;
import java.util.Date;

/**
 *
 */
@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @Column(name = "id")
    @Basic(optional = false)
    @GeneratedValue(generator = "clienteSec", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "clienteSec", sequenceName = "cliente_sec", allocationSize = 0)
    private int id;

    @Column(name = "nombre")
    @Basic(optional = false)
    private String nombre;

    @Column(name = "apellido")
    @Basic(optional = false)
    private String apellido;

    @Column(name = "nro_documento")
    @Basic(optional = false)
    private String nroDocumento;

    @Column(name = "tipo_documento")
    @Basic(optional = false)
    private String tipoDocumento;

    @Column(name = "nacionalidad")
    private String nacionalidad;

    @Column(name = "email")
    private String email;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getNroDocumento() {
        return nroDocumento;
    }

    public void setNroDocumento(String nroDocumento) {
        this.nroDocumento = nroDocumento;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }
}
