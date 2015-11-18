/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import mx.bidg.utils.DateTimeConverter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "C_VIEWS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class CViews implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_VIEW")
    @JsonView(JsonViews.Root.class)
    private Integer idView;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "VIEW_NAME")
    @JsonView(JsonViews.Root.class)
    private String viewName;

    @Size(max = 100)
    @Column(name = "VIEW")
    @JsonView(JsonViews.Root.class)
    private String view;

    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idView")
    @JsonView(JsonViews.Embedded.class)
    private List<ViewsRole> viewsRoleList;

    @JoinColumn(name = "ID_MODULE", referencedColumnName = "ID_MODULE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CModules idModule;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idView")
    @JsonView(JsonViews.Embedded.class)
    private List<ViewsComponent> viewsComponentList;

    public CViews() {
    }

    public CViews(Integer idView) {
        this.idView = idView;
    }

    public CViews(Integer idView, String viewName, LocalDateTime creationDate) {
        this.idView = idView;
        this.viewName = viewName;
        this.creationDate = creationDate;
    }

    public Integer getIdView() {
        return idView;
    }

    public void setIdView(Integer idView) {
        this.idView = idView;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    @XmlTransient
    public List<ViewsRole> getViewsRoleList() {
        return viewsRoleList;
    }

    public void setViewsRoleList(List<ViewsRole> viewsRoleList) {
        this.viewsRoleList = viewsRoleList;
    }

    public CModules getIdModule() {
        return idModule;
    }

    public void setIdModule(CModules idModule) {
        this.idModule = idModule;
    }

    @XmlTransient
    public List<ViewsComponent> getViewsComponentList() {
        return viewsComponentList;
    }

    public void setViewsComponentList(List<ViewsComponent> viewsComponentList) {
        this.viewsComponentList = viewsComponentList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idView != null ? idView.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CViews)) {
            return false;
        }
        CViews other = (CViews) object;
        if ((this.idView == null && other.idView != null) || (this.idView != null && !this.idView.equals(other.idView))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CViews[ idView=" + idView + " ]";
    }
    
}