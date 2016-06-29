package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import mx.bidg.config.JsonViews;
import mx.bidg.pojos.DateFormatsPojo;
import mx.bidg.utils.DateTimeConverter;
import org.hibernate.annotations.DynamicUpdate;

/**
 *
 * @author jolvera
 */
@Entity
@DynamicUpdate
@Table(name = "TRANSACTIONS")

@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class Transactions implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_TRANSACTION")
    @JsonView(JsonViews.Root.class)
    private Integer idTransaction;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "AMOUNT")
    @JsonView(JsonViews.Root.class)
    private BigDecimal amount;
    
    @Size(max = 45)
    @Column(name = "TRANSACTION_NUMBER")
    @JsonView(JsonViews.Root.class)
    private String transactionNumber;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "CREATION_DATE")
    @Convert(converter = DateTimeConverter.class)
    @JsonView(JsonViews.Root.class)
    private LocalDateTime creationDate;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID_ACCESS_LEVEL")
    @JsonView(JsonViews.Root.class)
    private int idAccessLevel;
    
    @Column(name = "ID_CURRENCY", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idCurrency;
    
    @Column(name="ID_ACCOUNT_PAYABLE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idAccountsPayable;
    
    @Column(name = "ID_TRANSACTION_STATUS",insertable = false,updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idTransactionsStatus;
    
    @Column(name ="ID_BALANCE",insertable = false,updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idBalance;
    
    @Column(name="ID_OPERATION_TYPE",insertable = false,updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idOperationTypes;

    @Column(name="ID_USER",insertable = false,updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idUser;
    
    @JoinColumn(name = "ID_CURRENCY", referencedColumnName = "ID_CURRENCY")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CCurrencies currencies;
    
    @JoinColumn(name = "ID_ACCOUNT_PAYABLE", referencedColumnName = "ID_ACCOUNT_PAYABLE")
    @ManyToOne(optional = true)
    @JsonView(JsonViews.Embedded.class)
    private AccountsPayable accountsPayable;
    
    @JoinColumn(name = "ID_TRANSACTION_STATUS", referencedColumnName = "ID_TRANSACTION_STATUS")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private CTransactionsStatus transactionsStatus;
    
    @JoinColumn(name = "ID_BALANCE", referencedColumnName = "ID_BALANCE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private Balances balances;
    
    @JoinColumn(name = "ID_OPERATION_TYPE", referencedColumnName = "ID_OPERATION_TYPE")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private COperationTypes operationTypes;

    @JoinColumn(name = "ID_USER", referencedColumnName = "ID_USER")
    @ManyToOne(optional = false)
    @JsonView(JsonViews.Embedded.class)
    private Users user;


    @JsonView(JsonViews.Root.class)
    @Column(name = "ID_EMPLOYEE", updatable = false, insertable = false)
    private Employees idEmployeeRequest;


    @JsonView(JsonViews.Root.class)
    @Column(name = "ID_ROLE", updatable = false, insertable = false)
    private CRoles idEmployeeRole;


    @JsonView(JsonViews.Root.class)
    @Column(name = "ID_GROUP", updatable = false, insertable = false)
    private CGroups idGroupRequest;


    @JsonView(JsonViews.Root.class)
    @Column(name = "ID_DISTRIBUTOR", updatable = false, insertable = false)
    private CDistributors idDistributorRequest;


    @JsonView(JsonViews.Root.class)
    @Column(name = "ID_REGION", updatable = false, insertable = false)
    private CRegions idRegionRequest;


    @JsonView(JsonViews.Root.class)
    @Column(name = "ID_BRANCH", updatable = false, insertable = false)
    private CBranchs idBranchRequest;


    @Column(name = "ID_AREA", updatable = false, insertable = false)
    @JsonView(JsonViews.Root.class)
    private CAreas idAreaRequest;


    @JsonView(JsonViews.Root.class)
    @Column(name = "ID_ACCOUNTING_ACCOUNT", updatable = false, insertable = false)
    private AccountingAccounts idAccountingAccount;

    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    @JoinColumn(name = "ID_EMPLOYEE",  referencedColumnName = "ID_EMPLOYEE")
    private Employees employeeRequest;

    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    @JoinColumn(name = "ID_ROLE", referencedColumnName = "ID_ROLE")
    private CRoles employeeRole;

    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    @JoinColumn(name = "ID_GROUP", referencedColumnName = "ID_GROUP")
    private CGroups groupRequest;

    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    @JoinColumn(name = "ID_DISTRIBUTOR", referencedColumnName = "ID_DISTRIBUTOR")
    private CDistributors distributorRequest;

    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    @JoinColumn(name = "ID_REGION", referencedColumnName = "ID_REGION")
    private CRegions regionRequest;

    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    @JoinColumn(name = "ID_BRANCH", referencedColumnName = "ID_BRANCH")
    private CBranchs branchRequest;

    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    @JoinColumn(name = "ID_AREA", referencedColumnName = "ID_AREA")
    private CAreas areaRequest;

    @ManyToOne
    @JsonView(JsonViews.Embedded.class)
    @JoinColumn(name = "ID_ACCOUNTING_ACCOUNT", referencedColumnName = "ID_ACCOUNTING_ACCOUNT")
    private AccountingAccounts accountingAccount;

    public Transactions() {
    }

    public Transactions(Integer idTransaction) {
        this.idTransaction = idTransaction;
    }

    public Transactions(Integer idTransaction, BigDecimal amount, LocalDateTime creationDate, int idAccessLevel) {
        this.idTransaction = idTransaction;
        this.amount = amount;
        this.creationDate = creationDate;
        this.idAccessLevel = idAccessLevel;
    }

    public Integer getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(Integer idTransaction) {
        this.idTransaction = idTransaction;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public DateFormatsPojo getCreationDateFormats() {
        return (creationDate == null) ? null : new DateFormatsPojo(creationDate);
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

    public CCurrencies getCurrencies() {
        return currencies;
    }

    public void setCurrencies(CCurrencies cCurrencies) {
        this.currencies = cCurrencies;
    }

    public AccountsPayable getAccountsPayable() {
        return accountsPayable;
    }

    public void setAccountsPayable(AccountsPayable accountsPayable) {
        this.accountsPayable = accountsPayable;
    }

    public CTransactionsStatus getTransactionsStatus() {
        return transactionsStatus;
    }

    public void setTransactionsStatus(CTransactionsStatus cTransactionsStatus) {
        this.transactionsStatus = cTransactionsStatus;
    }

    public Balances getBalances() {
        return balances;
    }

    public void setBalances(Balances balances) {
        this.balances = balances;
    }

    public COperationTypes getOperationTypes() {
        return operationTypes;
    }

    public void setOperationTypes(COperationTypes cOperationTypes) {
        this.operationTypes = cOperationTypes;
    }

    public Integer getIdCurrency() {
        return idCurrency;
    }

    public void setIdCurrency(Integer idCurrency) {
        this.idCurrency = idCurrency;
    }

    public Integer getIdAccountsPayable() {
        return idAccountsPayable;
    }

    public void setIdAccountsPayable(Integer idAccountsPayable) {
        this.idAccountsPayable = idAccountsPayable;
    }

    public Integer getIdTransactionsStatus() {
        return idTransactionsStatus;
    }

    public void setIdTransactionsStatus(Integer idTransactionsStatus) {
        this.idTransactionsStatus = idTransactionsStatus;
    }

    public Integer getIdBalance() {
        return idBalance;
    }

    public void setIdBalance(Integer idBalance) {
        this.idBalance = idBalance;
    }

    public Integer getIdOperationTypes() {
        return idOperationTypes;
    }

    public void setIdOperationTypes(Integer idOperationTypes) {
        this.idOperationTypes = idOperationTypes;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Employees getIdEmployeeRequest() {
        return idEmployeeRequest;
    }

    public void setIdEmployeeRequest(Employees idEmployeeRequest) {
        this.idEmployeeRequest = idEmployeeRequest;
    }

    public CRoles getIdEmployeeRole() {
        return idEmployeeRole;
    }

    public void setIdEmployeeRole(CRoles idEmployeeRole) {
        this.idEmployeeRole = idEmployeeRole;
    }

    public CGroups getIdGroupRequest() {
        return idGroupRequest;
    }

    public void setIdGroupRequest(CGroups idGroupRequest) {
        this.idGroupRequest = idGroupRequest;
    }

    public CDistributors getIdDistributorRequest() {
        return idDistributorRequest;
    }

    public void setIdDistributorRequest(CDistributors idDistributorRequest) {
        this.idDistributorRequest = idDistributorRequest;
    }

    public CRegions getIdRegionRequest() {
        return idRegionRequest;
    }

    public void setIdRegionRequest(CRegions idRegionRequest) {
        this.idRegionRequest = idRegionRequest;
    }

    public CBranchs getIdBranchRequest() {
        return idBranchRequest;
    }

    public void setIdBranchRequest(CBranchs idBranchRequest) {
        this.idBranchRequest = idBranchRequest;
    }

    public CAreas getIdAreaRequest() {
        return idAreaRequest;
    }

    public void setIdAreaRequest(CAreas idAreaRequest) {
        this.idAreaRequest = idAreaRequest;
    }

    public AccountingAccounts getIdAccountingAccount() {
        return idAccountingAccount;
    }

    public void setIdAccountingAccount(AccountingAccounts idAccountingAccount) {
        this.idAccountingAccount = idAccountingAccount;
    }

    public Employees getEmployeeRequest() {
        return employeeRequest;
    }

    public void setEmployeeRequest(Employees employeeRequest) {
        this.employeeRequest = employeeRequest;
    }

    public CRoles getEmployeeRole() {
        return employeeRole;
    }

    public void setEmployeeRole(CRoles employeeRole) {
        this.employeeRole = employeeRole;
    }

    public CGroups getGroupRequest() {
        return groupRequest;
    }

    public void setGroupRequest(CGroups groupRequest) {
        this.groupRequest = groupRequest;
    }

    public CDistributors getDistributorRequest() {
        return distributorRequest;
    }

    public void setDistributorRequest(CDistributors distributorRequest) {
        this.distributorRequest = distributorRequest;
    }

    public CRegions getRegionRequest() {
        return regionRequest;
    }

    public void setRegionRequest(CRegions regionRequest) {
        this.regionRequest = regionRequest;
    }

    public CBranchs getBranchRequest() {
        return branchRequest;
    }

    public void setBranchRequest(CBranchs branchRequest) {
        this.branchRequest = branchRequest;
    }

    public CAreas getAreaRequest() {
        return areaRequest;
    }

    public void setAreaRequest(CAreas areaRequest) {
        this.areaRequest = areaRequest;
    }

    public AccountingAccounts getAccountingAccount() {
        return accountingAccount;
    }

    public void setAccountingAccount(AccountingAccounts accountingAccount) {
        this.accountingAccount = accountingAccount;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTransaction != null ? idTransaction.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transactions)) {
            return false;
        }
        Transactions other = (Transactions) object;
        if ((this.idTransaction == null && other.idTransaction != null) || (this.idTransaction != null && !this.idTransaction.equals(other.idTransaction))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.Transactions[ idTransaction=" + idTransaction + " ]";
    }
    
}
