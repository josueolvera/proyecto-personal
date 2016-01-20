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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import mx.bidg.config.JsonViews;
import mx.bidg.utils.DateTimeConverter;

/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "PERIODICS_PAYMENTS")
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class PeriodicsPayments implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_PERIODIC_PAYMENT")
    @JsonView(JsonViews.Root.class)
    private Integer idPeriodicPayment;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "FOLIO")
    @JsonView(JsonViews.Root.class)
    private String folio;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal amount;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "INITIAL_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime initialDate;
    
    @Column(name = "NEXT_PAYMENT")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime nextPayment;
    
    @Column(name = "DUE_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime dueDate;
    
    @Column(name = "PAYMENT_NUM")
    @JsonView(JsonViews.Root.class)
    private Integer paymentNum;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private int idAccessLevel;
    
    @JoinColumn(name = "ID_PERIOD", referencedColumnName = "ID_PERIOD")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CPeriods idPeriod;
    
    @JoinColumn(name = "ID_PERIODIC_PAYMENT_STATUS", referencedColumnName = "ID_PERIODIC_PAYMENT_STATUS")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CPeriodicPaymentsStatus idPeriodicPaymentStatus;

    public PeriodicsPayments() {
    }

    public PeriodicsPayments(Integer idPeriodicPayment) {
        this.idPeriodicPayment = idPeriodicPayment;
    }

    public PeriodicsPayments(Integer idPeriodicPayment, String folio, BigDecimal amount, LocalDateTime initialDate, int idAccessLevel) {
        this.idPeriodicPayment = idPeriodicPayment;
        this.folio = folio;
        this.amount = amount;
        this.initialDate = initialDate;
        this.idAccessLevel = idAccessLevel;
    }

    public Integer getIdPeriodicPayment() {
        return idPeriodicPayment;
    }

    public void setIdPeriodicPayment(Integer idPeriodicPayment) {
        this.idPeriodicPayment = idPeriodicPayment;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(LocalDateTime initialDate) {
        this.initialDate = initialDate;
    }

    public LocalDateTime getNextPayment() {
        return nextPayment;
    }

    public void setNextPayment(LocalDateTime nextPayment) {
        this.nextPayment = nextPayment;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Integer getPaymentNum() {
        return paymentNum;
    }

    public void setPaymentNum(Integer paymentNum) {
        this.paymentNum = paymentNum;
    }

    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(int idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public CPeriods getIdPeriod() {
        return idPeriod;
    }

    public void setIdPeriod(CPeriods idPeriod) {
        this.idPeriod = idPeriod;
    }

    public CPeriodicPaymentsStatus getIdPeriodicPaymentStatus() {
        return idPeriodicPaymentStatus;
    }

    public void setIdPeriodicPaymentStatus(CPeriodicPaymentsStatus idPeriodicPaymentStatus) {
        this.idPeriodicPaymentStatus = idPeriodicPaymentStatus;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPeriodicPayment != null ? idPeriodicPayment.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PeriodicsPayments)) {
            return false;
        }
        PeriodicsPayments other = (PeriodicsPayments) object;
        if ((this.idPeriodicPayment == null && other.idPeriodicPayment != null) || (this.idPeriodicPayment != null && !this.idPeriodicPayment.equals(other.idPeriodicPayment))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.PeriodicsPayments[ idPeriodicPayment=" + idPeriodicPayment + " ]";
    }
    
}
