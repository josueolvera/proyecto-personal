package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.JsonViews;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by gerardo8 on 29/04/16.
 */
@Entity
@DynamicUpdate
@Table(name = "SAP_SALES")
public class SapSale implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID_SAP_SALE")
    @JsonView(JsonViews.Root.class)
    private Integer idSapSale;

    @Basic(optional = false)
    @Column(name = "ID_SALE")
    @JsonView(JsonViews.Root.class)
    private String idSale;

    @Column(name = "CREATION_DATE")
    @Temporal(TemporalType.DATE)
    @JsonView(JsonViews.Root.class)
    private Date creationDate;

    @Column(name = "INTERLOC_COM")
    private String interlocCom;

    @Column(name = "CLIENT_PARENT_LAST")
    @JsonView(JsonViews.Root.class)
    private String clientParentLast;

    @Column(name = "CLIENT_MOTHER_LAST")
    @JsonView(JsonViews.Root.class)
    private String clientMotherLast;

    @Column(name = "CLIENT_NAME")
    @JsonView(JsonViews.Root.class)
    private String clientName;

    @Column(name = "CLIENT_SEC_NAME")
    @JsonView(JsonViews.Root.class)
    private String clientSecName;

    @Column(name = "CLIENT_SINGLE_LAST")
    @JsonView(JsonViews.Root.class)
    private String clientSingleLast;

    @Column(name = "CLIENT_ID")
    @JsonView(JsonViews.Root.class)
    private String clientId;

    @Column(name = "IMSS_NUM")
    @JsonView(JsonViews.Root.class)
    private String imssNum;

    @Column(name = "AGREEMENT_NAME")
    @JsonView(JsonViews.Root.class)
    private String agreementName;

    @Column(name = "PRODUCT")
    @JsonView(JsonViews.Root.class)
    private String product;

    @Column(name = "DEPENDENCY")
    @JsonView(JsonViews.Root.class)
    private String dependency;

    @Column(name = "STATUS_SALE")
    @JsonView(JsonViews.Root.class)
    private String statusSale;

    @Column(name = "LAST_UPDATE")
    @Temporal(TemporalType.DATE)
    @JsonView(JsonViews.Root.class)
    private Date lastUpdate;

    @Column(name = "APPROVAL_DATE")
    @Temporal(TemporalType.DATE)
    @JsonView(JsonViews.Root.class)
    private Date approvalDate;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "REQUESTED_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal requestedAmount;

    @Column(name = "PAYMENTS")
    @JsonView(JsonViews.Root.class)
    private String payments;

    @Column(name = "DEPOSIT_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal depositAmount;

    @Column(name = "COMISSIONABLE_AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal comissionableAmount;

    @Column(name = "PURCHASE_DATE")
    @Temporal(TemporalType.DATE)
    @JsonView(JsonViews.Root.class)
    private Date purchaseDate;

    @Column(name = "COMPANY_NAME")
    @JsonView(JsonViews.Root.class)
    private String companyName;

    @Column(name = "DISTRIBUTOR_NAME")
    @JsonView(JsonViews.Root.class)
    private String distributorName;

    @Column(name = "CLAVE_SAP")
    @JsonView(JsonViews.Root.class)
    private String claveSap;

    @Column(name = "BRANCH_NAME")
    @JsonView(JsonViews.Root.class)
    private String branchName;

    @Column(name = "REGION")
    @JsonView(JsonViews.Root.class)
    private String region;

    @Column(name = "BONIFICATION")
    @JsonView(JsonViews.Root.class)
    private BigDecimal bonification;

    @Column(name = "LIQUIDATION")
    @JsonView(JsonViews.Root.class)
    private BigDecimal liquidation;

    @Column(name = "STATUS")
    @JsonView(JsonViews.Root.class)
    private int status;

    public SapSale() {
    }

    public SapSale(Integer idSapSale) {
        this.idSapSale = idSapSale;
    }

    public SapSale(Integer idSapSale, String idSale) {
        this.idSapSale = idSapSale;
        this.idSale = idSale;
    }

    public Integer getIdSapSale() {
        return idSapSale;
    }

    public void setIdSapSale(Integer idSapSale) {
        this.idSapSale = idSapSale;
    }

    public String getIdSale() {
        return idSale;
    }

    public void setIdSale(String idSale) {
        this.idSale = idSale;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getInterlocCom() {
        return interlocCom;
    }

    public void setInterlocCom(String interlocCom) {
        this.interlocCom = interlocCom;
    }

    public String getClientParentLast() {
        return clientParentLast;
    }

    public void setClientParentLast(String clientParentLast) {
        this.clientParentLast = clientParentLast;
    }

    public String getClientMotherLast() {
        return clientMotherLast;
    }

    public void setClientMotherLast(String clientMotherLast) {
        this.clientMotherLast = clientMotherLast;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientSecName() {
        return clientSecName;
    }

    public void setClientSecName(String clientSecName) {
        this.clientSecName = clientSecName;
    }

    public String getClientSingleLast() {
        return clientSingleLast;
    }

    public void setClientSingleLast(String clientSingleLast) {
        this.clientSingleLast = clientSingleLast;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getImssNum() {
        return imssNum;
    }

    public void setImssNum(String imssNum) {
        this.imssNum = imssNum;
    }

    public String getAgreementName() {
        return agreementName;
    }

    public void setAgreementName(String agreementName) {
        this.agreementName = agreementName;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getDependency() {
        return dependency;
    }

    public void setDependency(String dependency) {
        this.dependency = dependency;
    }

    public String getStatusSale() {
        return statusSale;
    }

    public void setStatusSale(String statusSale) {
        this.statusSale = statusSale;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Date getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(Date approvalDate) {
        this.approvalDate = approvalDate;
    }

    public BigDecimal getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(BigDecimal requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public String getPayments() {
        return payments;
    }

    public void setPayments(String payments) {
        this.payments = payments;
    }

    public BigDecimal getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(BigDecimal depositAmount) {
        this.depositAmount = depositAmount;
    }

    public BigDecimal getComissionableAmount() {
        return comissionableAmount;
    }

    public void setComissionableAmount(BigDecimal comissionableAmount) {
        this.comissionableAmount = comissionableAmount;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getDistributorName() {
        return distributorName;
    }

    public void setDistributorName(String distributorName) {
        this.distributorName = distributorName;
    }

    public String getClaveSap() {
        return claveSap;
    }

    public void setClaveSap(String claveSap) {
        this.claveSap = claveSap;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public BigDecimal getBonification() {
        return bonification;
    }

    public void setBonification(BigDecimal bonification) {
        this.bonification = bonification;
    }

    public BigDecimal getLiquidation() {
        return liquidation;
    }

    public void setLiquidation(BigDecimal liquidation) {
        this.liquidation = liquidation;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }



    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSapSale != null ? idSapSale.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SapSale)) {
            return false;
        }
        SapSale other = (SapSale) object;
        if ((this.idSapSale == null && other.idSapSale != null) || (this.idSapSale != null && !this.idSapSale.equals(other.idSapSale))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.SapSale[ idSapSale=" + idSapSale + " ]";
    }

}

