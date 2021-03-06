/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author rubens
 */
@Entity
@DynamicUpdate
@Table(name = "C_ASENTAMIENTOS")

public class CAsentamientos implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ASENTAMIENTO")
    @JsonView(JsonViews.Root.class)
    private Integer idAsentamiento;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "NOMBRE_ASENTAMIENTO")
    @JsonView(JsonViews.Root.class)
    private String nombreAsentamiento;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private int idAccessLevel;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_TIPO_ASENTAMIENTO")
    @JsonView(JsonViews.Root.class)
    private int idTipoAsentamiento;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ESTADO")
    @JsonView(JsonViews.Root.class)
    private int idEstado;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_MUNICIPIO")
    @JsonView(JsonViews.Root.class)
    private int idMunicipio;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "CODIGO_POSTAL")
    @JsonView(JsonViews.Root.class)
    private String codigoPostal;

    @Transient
    @JsonView(JsonViews.Embedded.class)
    private CMunicipios municipios;

    public CAsentamientos() {
    }

    public CAsentamientos(Integer idAsentamiento) {
        this.idAsentamiento = idAsentamiento;
    }

    public CAsentamientos(Integer idAsentamiento, String nombreAsentamiento, int idAccessLevel, int idTipoAsentamiento, int idEstado, int idMunicipio, String codigoPostal) {
        this.idAsentamiento = idAsentamiento;
        this.nombreAsentamiento = nombreAsentamiento;
        this.idAccessLevel = idAccessLevel;
        this.idTipoAsentamiento = idTipoAsentamiento;
        this.idEstado = idEstado;
        this.idMunicipio = idMunicipio;
        this.codigoPostal = codigoPostal;
    }

    public Integer getIdAsentamiento() {
        return idAsentamiento;
    }

    public void setIdAsentamiento(Integer idAsentamiento) {
        this.idAsentamiento = idAsentamiento;
    }

    public String getNombreAsentamiento() {
        return nombreAsentamiento;
    }

    public void setNombreAsentamiento(String nombreAsentamiento) {
        this.nombreAsentamiento = nombreAsentamiento;
    }

    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(int idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public int getIdTipoAsentamiento() {
        return idTipoAsentamiento;
    }

    public void setIdTipoAsentamiento(int idTipoAsentamiento) {
        this.idTipoAsentamiento = idTipoAsentamiento;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public int getIdMunicipio() {
        return idMunicipio;
    }

    public void setIdMunicipio(int idMunicipio) {
        this.idMunicipio = idMunicipio;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public CMunicipios getMunicipios() {
        return municipios;
    }

    public void setMunicipios(CMunicipios municipios) {
        this.municipios = municipios;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idAsentamiento != null ? idAsentamiento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CAsentamientos)) {
            return false;
        }
        CAsentamientos other = (CAsentamientos) object;
        if ((this.idAsentamiento == null && other.idAsentamiento != null) || (this.idAsentamiento != null && !this.idAsentamiento.equals(other.idAsentamiento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CAsentamientos[ idAsentamiento=" + idAsentamiento + " ]";
    }
    
}
