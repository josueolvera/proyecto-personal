/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import mx.bidg.utils.DateTimeConverter;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author rafael
 */
@Entity
@DynamicUpdate
@Table(name = "C_FOLIOS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CFolios implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_FOLIO")
    @JsonView(JsonViews.Root.class)
    private Integer idFolio;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "FOLIO")
    @JsonView(JsonViews.Root.class)
    private String folio;

    @Column(name = "CREATION_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime creationDate;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private int idAccessLevel;

    @JoinColumn(name = "ID_TABLE", referencedColumnName = "ID_TABLE")
    @ManyToOne
    @JsonProperty("cTables")
    @JsonView(JsonViews.Embedded.class)
    private CTables cTables;

    public CFolios() {
    }

    public CFolios(Integer idFolio) {
        this.idFolio = idFolio;
    }

    public CFolios(String folio, CTables table) {
        this.folio = folio;
        this.idAccessLevel = 1;
        this.creationDate = LocalDateTime.now();
        this.cTables = table;
    }

    public CFolios(Integer idFolio, String folio, int idAccessLevel) {
        this.idFolio = idFolio;
        this.folio = folio;
        this.idAccessLevel = idAccessLevel;
    }

    public Integer getIdFolio() {
        return idFolio;
    }

    public void setIdFolio(Integer idFolio) {
        this.idFolio = idFolio;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(int idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    @JsonProperty("cTables")
    public CTables getCTables() {
        return cTables;
    }

    public void setCTables(CTables cTables) {
        this.cTables = cTables;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idFolio != null ? idFolio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CFolios)) {
            return false;
        }
        CFolios other = (CFolios) object;
        if ((this.idFolio == null && other.idFolio != null) || (this.idFolio != null && !this.idFolio.equals(other.idFolio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CFolios[ idFolio=" + idFolio + " ]";
    }
}
