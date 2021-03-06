package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author PC_YAIR
 */
@Entity
@DynamicUpdate
@Table(name = "C_TYPE_SECURE")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class , property = "_id")
public class CTypeSecure implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_TYPE_SECURE")
    @JsonView(JsonViews.Root.class)
    private Integer idTypeSecure;

    @Size(max = 35)
    @Column(name = "NAME")
    @JsonView(JsonViews.Root.class)
    private String name;

    @Size(max = 3)
    @Column(name = "TYPE_SECURE")
    @JsonView(JsonViews.Root.class)
    private String typeSecure;

    public CTypeSecure() {
    }

    public CTypeSecure(Integer idTypeSecure) {
        this.idTypeSecure = idTypeSecure;
    }

    public Integer getIdTypeSecure() {
        return idTypeSecure;
    }

    public void setIdTypeSecure(Integer idTypeSecure) {
        this.idTypeSecure = idTypeSecure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeSecure() {
        return typeSecure;
    }

    public void setTypeSecure(String typeSecure) {
        this.typeSecure = typeSecure;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTypeSecure != null ? idTypeSecure.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CTypeSecure)) {
            return false;
        }
        CTypeSecure other = (CTypeSecure) object;
        if ((this.idTypeSecure == null && other.idTypeSecure != null) || (this.idTypeSecure != null && !this.idTypeSecure.equals(other.idTypeSecure))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.dao.CTypeSecure[ idTypeSecure=" + idTypeSecure + " ]";
    }

}
