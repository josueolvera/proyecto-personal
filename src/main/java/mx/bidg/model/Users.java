package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import mx.bidg.config.AccessLevelFilterable;
import mx.bidg.config.JsonViews;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import mx.bidg.utils.DateTimeConverter;
import mx.bidg.utils.TimeConverter;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;


/**
 *
 * @author sistemask
 */
@Entity
@Table(name = "USERS")
@DynamicUpdate
@SelectBeforeUpdate
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "_id")
public class Users implements Serializable, AccessLevelFilterable {
    
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USER")
    @JsonView(JsonViews.Root.class)
    private Integer idUser;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "USERNAME", updatable = false)
    @JsonView(JsonViews.Root.class)
    private String username;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 35)
    @Column(name = "PASSWORD")
    @JsonView(JsonViews.Private.class)
    private String password;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "MAIL")
    @JsonView(JsonViews.Root.class)
    private String mail;

    @Basic(optional = false)
    @NotNull
    @Column(name = "STATUS")
    @JsonView(JsonViews.Root.class)
    private int status;

    @Basic(optional = false)
    @NotNull
    @Column(name = "ACTIVE_SESSION")
    @JsonView(JsonViews.Root.class)
    private int activeSession;

    @Column(name = "ID_DW_EMPLOYEE", insertable = false, updatable = false)
    @JsonView(JsonViews.Root.class)
    private Integer idDwemployee;

    @OneToOne
    @JoinColumn(name="ID_DW_EMPLOYEE", referencedColumnName = "ID_DW_EMPLOYEE", nullable=true, insertable=true, updatable=true)
    @JsonView(JsonViews.Embedded.class)
    private DwEmployees dwEmployee;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "TIME_SESSION")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = TimeConverter.class)
    private LocalTime timeSession;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "HIGH_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime highDate;
    
    @Column(name = "MODIFICATION_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime modificationDate;
    
    @Column(name = "LOW_DATE")
    @JsonView(JsonViews.Root.class)
    @Convert(converter = DateTimeConverter.class)
    private LocalDateTime lowDate;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonView(JsonViews.Embedded.class)
    private List<UsersRole> usersRoleList;

    @Column(name = "ID_ACCESS_LEVEL")
    private Integer idAccessLevel;

    @Transient
    @JsonView(JsonViews.Root.class)
    private Set<Integer> accessLevels;

    public Users() {
    }

    public Users(Integer idUser) {
        this.idUser = idUser;
    }

    public Users(Integer idUser, String username, String password, String mail, int status, int activeSession, LocalTime timeSession, LocalDateTime highDate) {
        this.idUser = idUser;
        this.username = username;
        this.password = password;
        this.mail = mail;
        this.status = status;
        this.activeSession = activeSession;
        this.timeSession = timeSession;
        this.highDate = highDate;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getActiveSession() {
        return activeSession;
    }

    public void setActiveSession(int activeSession) {
        this.activeSession = activeSession;
    }

    public LocalTime getTimeSession() {
        return timeSession;
    }

    public void setTimeSession(LocalTime timeSession) {
        this.timeSession = timeSession;
    }

    public LocalDateTime getHighDate() {
        return highDate;
    }

    public void setHighDate(LocalDateTime highDate) {
        this.highDate = highDate;
    }

    public LocalDateTime getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDateTime modificationDate) {
        this.modificationDate = modificationDate;
    }

    public LocalDateTime getLowDate() {
        return lowDate;
    }

    public void setLowDate(LocalDateTime lowDate) {
        this.lowDate = lowDate;
    }
    
    public DwEmployees getDwEmployee() {
        return dwEmployee;
    }

    public void setDwEmployee(DwEmployees dwEmployee) {
        this.dwEmployee = dwEmployee;
    }

    public Integer getIdDwemployee() {
        return idDwemployee;
    }

    public void setIdDwemployee(Integer idDwemployee) {
        this.idDwemployee = idDwemployee;
    }

    public List<UsersRole> getUsersRoleList() {
        return usersRoleList;
    }

    public void setUsersRoleList(List<UsersRole> usersRoleList) {
        this.usersRoleList = usersRoleList;
    }

    public Integer getIdAccessLevel() {
        return idAccessLevel;
    }

    public void setIdAccessLevel(Integer idAccessLevel) {
        this.idAccessLevel = idAccessLevel;
    }

    public Set<Integer> getAccessLevels() {
        return accessLevels;
    }

    public void setAccessLevels(Set<Integer> accessLevels) {
        this.accessLevels = accessLevels;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUser != null ? idUser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.idUser == null && other.idUser != null) || (this.idUser != null && !this.idUser.equals(other.idUser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Users{" + "idUser=" + idUser + ", username=" + username + ", password=" + password + ", mail=" + mail + ", status=" + status + ", activeSession=" + activeSession + ", timeSession=" + timeSession + ", highDate=" + highDate + ", modificationDate=" + modificationDate + ", lowDate=" + lowDate + "'}'";
    }

    }
