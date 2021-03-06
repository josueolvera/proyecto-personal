/**
 *
 * @author rafael
 */
package mx.bidg.model;

import com.fasterxml.jackson.annotation.JsonView;
import mx.bidg.config.JsonViews;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
@Table(name = "C_DATA_TYPES")
public class CDataTypes implements Serializable {

    public static final int ID_VARCHAR = 1;
    public static final int ID_INT = 2;
    public static final int ID_DECIMAL_12_4 = 3;
    public static final int ID_DECIMAL_14_2 = 4;
    public static final int ID_TIMESTAMP = 5;
    public static final int ID_DATE = 6;
    public static final int ID_DATETIME = 7;
    public static final int ID_TEXT = 8;
    public static final int ID_NUMERIC = 9;

    public static final CDataTypes VARCHAR = new CDataTypes(ID_VARCHAR);
    public static final CDataTypes INT = new CDataTypes(ID_INT);
    public static final CDataTypes DECIMAL_12_4 = new CDataTypes(ID_DECIMAL_12_4);
    public static final CDataTypes DECIMAL_14_2 = new CDataTypes(ID_DECIMAL_14_2);
    public static final CDataTypes TIMESTAMP = new CDataTypes(ID_TIMESTAMP);
    public static final CDataTypes DATE = new CDataTypes(ID_DATE);
    public static final CDataTypes DATETIME = new CDataTypes(ID_DATETIME);
    public static final CDataTypes TEXT = new CDataTypes(ID_TEXT);
    public static final CDataTypes NUMERIC = new CDataTypes(ID_NUMERIC);

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DATA_TYPE")
    @JsonView(JsonViews.Root.class)
    private Integer idDataType;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 300)
    @Column(name = "DATA_NAME")
    @JsonView(JsonViews.Root.class)
    private String dataName;

    public CDataTypes() {
    }

    public CDataTypes(Integer idDataType) {
        this.idDataType = idDataType;
    }

    public CDataTypes(Integer idDataType, String dataName) {
        this.idDataType = idDataType;
        this.dataName = dataName;
    }

    public Integer getIdDataType() {
        return idDataType;
    }

    public void setIdDataType(Integer idDataType) {
        this.idDataType = idDataType;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDataType != null ? idDataType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CDataTypes)) {
            return false;
        }
        CDataTypes other = (CDataTypes) object;
        if ((this.idDataType == null && other.idDataType != null) || (this.idDataType != null && !this.idDataType.equals(other.idDataType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "mx.bidg.model.CDataTypes[ idDataType=" + idDataType + " ]";
    }
}
