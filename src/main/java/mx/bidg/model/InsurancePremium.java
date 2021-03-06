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
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 *
 * @author Kevin Salvador
 */
@Entity
@DynamicUpdate
@Table(name = "INSURANCE_PREMIUM")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class InsurancePremium implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_INSURANCE_PREMIUM")
    @JsonView(JsonViews.Root.class)
    private Integer idInsurancePremium;

    @Column(name = "ID_AMOUNTS_SECURE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idAmountsSecure;

    @Column(name = "ID_TYPE_SECURE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idTypeSecure;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "COMMISSION_ALTERNA")
    @JsonView(JsonViews.Root.class)
    private BigDecimal commissionAlterna;

    @Column(name = "COMMISSION_INTERPRO")
    @JsonView(JsonViews.Root.class)
    private BigDecimal commissionInterpro;

    @Column(name = "IVA_ALTERNA")
    @JsonView(JsonViews.Root.class)
    private BigDecimal ivaAlterna;

    @Column(name = "IVA_INTERPRO")
    @JsonView(JsonViews.Root.class)
    private BigDecimal ivaInterpro;

    @JoinColumn(name = "ID_AMOUNTS_SECURE", referencedColumnName = "ID_AMOUNTS_SECURE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CAmountsSecure amountsSecure;

    @JoinColumn(name = "ID_TYPE_SECURE", referencedColumnName = "ID_TYPE_SECURE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CTypeSecure typeSecure;

    public InsurancePremium() {
    }

    public InsurancePremium(Integer idInsurancePremium) {
        this.idInsurancePremium = idInsurancePremium;
    }

    public Integer getIdInsurancePremium() {
        return idInsurancePremium;
    }

    public void setIdInsurancePremium(Integer idInsurancePremium) {
        this.idInsurancePremium = idInsurancePremium;
    }

    public Integer getIdAmountsSecure() {
        return idAmountsSecure;
    }

    public void setIdAmountsSecure(Integer idAmountsSecure) {
        this.idAmountsSecure = idAmountsSecure;
    }

    public Integer getIdTypeSecure() {
        return idTypeSecure;
    }

    public void setIdTypeSecure(Integer idTypeSecure) {
        this.idTypeSecure = idTypeSecure;
    }

    public CAmountsSecure getAmountsSecure() {
        return amountsSecure;
    }

    public void setAmountsSecure(CAmountsSecure amountsSecure) {
        this.amountsSecure = amountsSecure;
    }

    public CTypeSecure getTypeSecure() {
        return typeSecure;
    }

    public void setTypeSecure(CTypeSecure typeSecure) {
        this.typeSecure = typeSecure;
    }

    public BigDecimal getCommissionAlterna() {
        return commissionAlterna;
    }

    public void setCommissionAlterna(BigDecimal commissionAlterna) {
        this.commissionAlterna = commissionAlterna;
    }

    public BigDecimal getCommissionInterpro() {
        return commissionInterpro;
    }

    public void setCommissionInterpro(BigDecimal commissionInterpro) {
        this.commissionInterpro = commissionInterpro;
    }

    public BigDecimal getIvaAlterna() {
        return ivaAlterna;
    }

    public void setIvaAlterna(BigDecimal ivaAlterna) {
        this.ivaAlterna = ivaAlterna;
    }

    public BigDecimal getIvaInterpro() {
        return ivaInterpro;
    }

    public void setIvaInterpro(BigDecimal ivaInterpro) {
        this.ivaInterpro = ivaInterpro;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idInsurancePremium != null ? idInsurancePremium.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof InsurancePremium)) {
            return false;
        }
        InsurancePremium other = (InsurancePremium) object;
        if ((this.idInsurancePremium == null && other.idInsurancePremium != null) || (this.idInsurancePremium != null && !this.idInsurancePremium.equals(other.idInsurancePremium))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.InsurancePremium[ idInsurancePremium=" + idInsurancePremium + " ]";
    }
    
}
