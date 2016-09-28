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
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import mx.bidg.config.JsonViews;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;


/**
 *
 * @author sistemask
 */
@Entity
@DynamicUpdate
@Table(name = "REQUESTS")

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class Requests implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_REQUEST")
    @JsonView(JsonViews.Root.class)
    private Integer idRequest;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "FOLIO")
    @JsonView(JsonViews.Root.class)
    private String folio;

    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "DESCRIPTION")
    @JsonView(JsonViews.Root.class)
    private String description;
    
    @Lob
    @Size(max = 65535)
    @Column(name = "PURPOSE")
    @JsonView(JsonViews.Root.class)
    private String purpose;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE", updatable = false)
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime creationDate;

    @Column(name = "APPLYING_DATE", updatable = false)
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime applyingDate;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private int idAccessLevel;
    
    @Column(name = "USER_REQUEST", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idUserRequest;
    
    @Column(name = "USER_RESPONSIBLE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idUserResponsible;
    
    @Column(name = "ID_BUDGET_YEAR_CONCEPT", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idBudgetYearConcept;
    
    @Column(name = "ID_MONTH", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idMonth;
    
    @Column(name = "ID_REQUEST_STATUS", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idRequestStatus;
    
    @JoinColumn(name = "USER_REQUEST", referencedColumnName = "ID_USER")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private Users userRequest;
    
    @JoinColumn(name = "USER_RESPONSIBLE", referencedColumnName = "ID_USER")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private Users userResponsible;
    
    @JoinColumn(name = "ID_BUDGET_YEAR_CONCEPT", referencedColumnName = "ID_BUDGET_YEAR_CONCEPT")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private BudgetYearConcept budgetYearConcept;

    @JoinColumn(name = "ID_MONTH", referencedColumnName = "ID_MONTH")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CMonths month;
    
    @JoinColumn(name = "ID_REQUEST_STATUS", referencedColumnName = "ID_REQUEST_STATUS")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CRequestStatus requestStatus;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "request")
    @JsonView(JsonViews.Embedded.class)
    private List<PriceEstimations> priceEstimationsList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "request")
    @JsonView(JsonViews.Embedded.class)
    private List<RequestProducts> requestProductsList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "request")
    @JsonView(JsonViews.Embedded.class)
    private List<RequestConcept> requestConceptList;

    public Requests() {
    }

    public Integer getIdRequest() {
        return idRequest;
    }

    public void setIdRequest(Integer idRequest) {
        this.idRequest = idRequest;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getApplyingDate() {
        return applyingDate;
    }

    public void setApplyingDate(LocalDateTime applyingDate) {
        this.applyingDate = applyingDate;
    }

    public int getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(int idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public Integer getIdUserRequest() {
        return idUserRequest;
    }

    public void setIdUserRequest(Integer idUserRequest) {
        this.idUserRequest = idUserRequest;
    }

    public Integer getIdUserResponsible() {
        return idUserResponsible;
    }

    public void setIdUserResponsible(Integer idUserResponsible) {
        this.idUserResponsible = idUserResponsible;
    }

    public Integer getIdBudgetYearConcept() {
        return idBudgetYearConcept;
    }

    public void setIdBudgetYearConcept(Integer idBudgetYearConcept) {
        this.idBudgetYearConcept = idBudgetYearConcept;
    }

    public Integer getIdMonth() {
        return idMonth;
    }

    public void setIdMonth(Integer idMonth) {
        this.idMonth = idMonth;
    }

    public Integer getIdRequestStatus() {
        return idRequestStatus;
    }

    public void setIdRequestStatus(Integer idRequestStatus) {
        this.idRequestStatus = idRequestStatus;
    }

    public Users getUserRequest() {
        return userRequest;
    }

    public void setUserRequest(Users userRequest) {
        this.userRequest = userRequest;
    }

    public Users getUserResponsible() {
        return userResponsible;
    }

    public void setUserResponsible(Users userResponsible) {
        this.userResponsible = userResponsible;
    }

    public BudgetYearConcept getBudgetYearConcept() {
        return budgetYearConcept;
    }

    public void setBudgetYearConcept(BudgetYearConcept budgetYearConcept) {
        this.budgetYearConcept = budgetYearConcept;
    }

    public CMonths getMonth() {
        return month;
    }

    public void setMonth(CMonths month) {
        this.month = month;
    }

    public CRequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(CRequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }

    public List<PriceEstimations> getPriceEstimationsList() {
        return priceEstimationsList;
    }

    public void setPriceEstimationsList(List<PriceEstimations> priceEstimationsList) {
        this.priceEstimationsList = priceEstimationsList;
    }

    public List<RequestProducts> getRequestProductsList() {
        return requestProductsList;
    }

    public void setRequestProductsList(List<RequestProducts> requestProductsList) {
        this.requestProductsList = requestProductsList;
    }

    public List<RequestConcept> getRequestConceptList() {
        return requestConceptList;
    }

    public void setRequestConceptList(List<RequestConcept> requestConceptList) {
        this.requestConceptList = requestConceptList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Requests requests = (Requests) o;

        return idRequest != null ? idRequest.equals(requests.idRequest) : requests.idRequest == null;

    }

    @Override
    public int hashCode() {
        return idRequest != null ? idRequest.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Requests{" +
                "idRequest=" + idRequest +
                ", folio='" + folio + '\'' +
                ", description='" + description + '\'' +
                ", purpose='" + purpose + '\'' +
                ", creationDate=" + creationDate +
                ", applyingDate=" + applyingDate +
                ", idAccessLevel=" + idAccessLevel +
                ", idUserRequest=" + idUserRequest +
                ", idUserResponsable=" + idUserResponsible +
                ", idBudgetYearConcept=" + idBudgetYearConcept +
                ", idMonth=" + idMonth +
                ", idRequestStatus=" + idRequestStatus +
                ", userRequest=" + userRequest +
                ", userResponsable=" + userResponsible +
                ", budgetYearConcept=" + budgetYearConcept +
                ", month=" + month +
                ", requestStatus=" + requestStatus +
                ", priceEstimationsList=" + priceEstimationsList +
                ", requestProductsList=" + requestProductsList +
                '}';
    }
}
