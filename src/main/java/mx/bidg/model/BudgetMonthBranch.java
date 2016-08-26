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
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import mx.bidg.config.JsonViews;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;


/**
 *
 * @author sistemask
 */
@Entity
@DynamicUpdate
@Table(name = "BUDGET_MONTH_BRANCH")

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class BudgetMonthBranch implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_BUDGET_MONTH_BRANCH")
    @JsonView(JsonViews.Root.class)
    private Integer idBudgetMonthBranch;

    @Basic(optional = false)
    @NotNull
    @Column(name = "AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal amount;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "EXPENDED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal expendedAmount;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "YEAR")
    @JsonView(JsonViews.Root.class)
    private int year;

    @JoinColumn(name = "ID_BUDGET", referencedColumnName = "ID_BUDGET")
    @ManyToOne(optional = false)
    @JsonView({JsonViews.Embedded.class, JsonViews.EmbeddedBudget.class})
    private Budgets budget;

    @JoinColumn(name = "ID_MONTH", referencedColumnName = "ID_MONTH")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CMonths month;
    
    @JoinColumn(name = "ID_CURRENCY", referencedColumnName = "ID_CURRENCY")
    @ManyToOne(optional = false)
    @JsonView({JsonViews.Embedded.class, JsonViews.EmbeddedBudget.class})
    private CCurrencies currency;
    
    @Column(name = "ID_BUDGET", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idBudget;
    
    @Column(name = "ID_MONTH", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idMonth;
    
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private Integer idAccessLevel;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "IS_AUTHORIZED")
    @JsonView(JsonViews.Root.class)
    private Integer isAuthorized;

    @Column(name = "USERNAME")
    @JsonView(JsonViews.Root.class)
    private String username;

    @Column(name = "CREATION_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;
    
    @Column(name = "ID_CURRENCY", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idCurrency;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "budgetMonthBranch")
    @JsonView(JsonViews.Embedded.class)
    private List<Requests> requestsList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "budgetMonthBranch")
    @JsonView(JsonViews.Embedded.class)
    private List<BudgetMonthConcepts> budgetMonthConceptsList;

    public BudgetMonthBranch() {
    }

    public BudgetMonthBranch(Integer idBudgetMonthBranch) {
        this.idBudgetMonthBranch = idBudgetMonthBranch;
    }

    public BudgetMonthBranch(Integer idBudgetMonthBranch, BigDecimal amount, int year) {
        this.idBudgetMonthBranch = idBudgetMonthBranch;
        this.amount = amount;
        this.year = year;
    }

    public BudgetMonthBranch(Integer idBudgetMonthBranch, BigDecimal amount, int year, Budgets budget, CMonths month) {
        this.idBudgetMonthBranch = idBudgetMonthBranch;
        this.amount = amount;
        this.year = year;
        this.budget = budget;
        this.month = month;
    }

    public Integer getIdBudgetMonthBranch() {
        return idBudgetMonthBranch;
    }

    public void setIdBudgetMonthBranch(Integer idBudgetMonthBranch) {
        this.idBudgetMonthBranch = idBudgetMonthBranch;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getExpendedAmount() {
        return expendedAmount;
    }

    public void setExpendedAmount(BigDecimal expendedAmount) {
        this.expendedAmount = expendedAmount;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Budgets getBudget() {
        return budget;
    }

    public void setBudget(Budgets budget) {
        this.budget = budget;
    }

    public CMonths getMonth() {
        return month;
    }

    public void setMonth(CMonths month) {
        this.month = month;
    }

    public Integer getIdBudget() {
        return idBudget;
    }

    public void setIdBudget(Integer idBudget) {
        this.idBudget = idBudget;
    }

    public Integer getIdMonth() {
        return idMonth;
    }

    public void setIdMonth(Integer idMonth) {
        this.idMonth = idMonth;
    }

    public Integer getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(Integer idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getIsAuthorized() {
        return isAuthorized;
    }

    public void setIsAuthorized(Integer isAuthorized) {
        this.isAuthorized = isAuthorized;
    }

    public CCurrencies getCurrency() {
        return currency;
    }

    public void setCurrency(CCurrencies currency) {
        this.currency = currency;
    }

    public List<Requests> getRequestsList() {
        return requestsList;
    }

    public void setRequestsList(List<Requests> requestsList) {
        this.requestsList = requestsList;
    }

    public List<BudgetMonthConcepts> getBudgetMonthConceptsList() {
        return budgetMonthConceptsList;
    }

    public void setBudgetMonthConceptsList(List<BudgetMonthConcepts> budgetMonthConceptsList) {
        this.budgetMonthConceptsList = budgetMonthConceptsList;
    }

    public Integer getIdCurrency() {
        return idCurrency;
    }

    public void setIdCurrency(Integer idCurrency) {
        this.idCurrency = idCurrency;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBudgetMonthBranch != null ? idBudgetMonthBranch.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BudgetMonthBranch)) {
            return false;
        }
        BudgetMonthBranch other = (BudgetMonthBranch) object;
        if ((this.idBudgetMonthBranch == null && other.idBudgetMonthBranch != null) || (this.idBudgetMonthBranch != null && !this.idBudgetMonthBranch.equals(other.idBudgetMonthBranch))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.BudgetMonthBranch[ idBudgetMonthBranch=" + idBudgetMonthBranch + " ]";
    }
    
}
