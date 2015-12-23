/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import mx.bidg.config.JsonViews;

/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "PROVIDERS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class Providers implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PROVIDER")
    @JsonView(JsonViews.Root.class)
    private Integer idProvider;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "PROVIDER_NAME")
    @JsonView(JsonViews.Root.class)
    private String providerName;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 13)
    @Column(name = "RFC")
    @JsonView(JsonViews.Root.class)
    private String rfc;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    private int idAccessLevel;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idProvider")
    @JsonView(JsonViews.Embedded.class)
    private List<ProvidersAccounts> providersAccountsList;

    public Providers() {
    }

    public Providers(Integer idProvider) {
        this.idProvider = idProvider;
    }

    public Providers(Integer idProvider, String providerName, String rfc) {
        this.idProvider = idProvider;
        this.providerName = providerName;
        this.rfc = rfc;
    }

    public Integer getIdProvider() {
        return idProvider;
    }

    public void setIdProvider(Integer idProvider) {
        this.idProvider = idProvider;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getRfc() {
        return rfc;
    }

    public void setRfc(String rfc) {
        this.rfc = rfc;
    }

    public List<ProvidersAccounts> getProvidersAccountsList() {
        return providersAccountsList;
    }

    public void setProvidersAccountsList(List<ProvidersAccounts> providersAccountsList) {
        this.providersAccountsList = providersAccountsList;
    }

    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(int idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProvider != null ? idProvider.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Providers)) {
            return false;
        }
        Providers other = (Providers) object;
        if ((this.idProvider == null && other.idProvider != null) || (this.idProvider != null && !this.idProvider.equals(other.idProvider))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.Providers[ idProvider=" + idProvider + " ]";
    }
    
}