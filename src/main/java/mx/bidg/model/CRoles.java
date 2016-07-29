/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;


/**
 *
 * @author sistemask
 */
@Entity
@DynamicUpdate
@Table(name = "C_ROLES")

public class CRoles implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_ROLE")
    @JsonView(JsonViews.Root.class)
    private Integer idRole;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ROLE_NAME")
    @JsonView(JsonViews.Root.class)
    private String roleName;

    @ManyToMany(mappedBy = "roles")
    @JsonView(JsonViews.Embedded.class)
    private Set<CAreas> areas;

    public CRoles() {
    }

    public CRoles(Integer idRole) {
        this.idRole = idRole;
    }

    public CRoles(Integer idRole, String roleName) {
        this.idRole = idRole;
        this.roleName = roleName;
    }

    public Integer getIdRole() {
        return idRole;
    }

    public void setIdRole(Integer idRole) {
        this.idRole = idRole;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<CAreas> getAreas() {
        return areas;
    }

    public void setAreas(Set<CAreas> areas) {
        this.areas = areas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idRole != null ? idRole.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CRoles)) {
            return false;
        }
        CRoles other = (CRoles) object;
        if ((this.idRole == null && other.idRole != null) || (this.idRole != null && !this.idRole.equals(other.idRole))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CRoles{" +
                "idRole=" + idRole +
                ", roleName='" + roleName + '\'' +
                ", areas=" + areas +
                '}';
    }
}
