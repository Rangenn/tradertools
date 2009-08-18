/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

/**
 *
 * @author е
 */
@SequenceGenerator(
    name="SEQ_GEN",
    sequenceName="supply_change_id_seq",
    allocationSize=1
)
@Entity
@Table(name = "supply_change")
@NamedQueries({@NamedQuery(name = "SupplyChange.findAll", query = "SELECT s FROM SupplyChange s"), @NamedQuery(name = "SupplyChange.findById", query = "SELECT s FROM SupplyChange s WHERE s.id = :id"), @NamedQuery(name = "SupplyChange.findByAmountLeft", query = "SELECT s FROM SupplyChange s WHERE s.amountLeft = :amountLeft"), @NamedQuery(name = "SupplyChange.findByPrice", query = "SELECT s FROM SupplyChange s WHERE s.price = :price"), @NamedQuery(name = "SupplyChange.findByFromDate", query = "SELECT s FROM SupplyChange s WHERE s.fromDate = :fromDate"), @NamedQuery(name = "SupplyChange.findByToDate", query = "SELECT s FROM SupplyChange s WHERE s.toDate = :toDate")})
public class SupplyChange implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    //@Generated(GenerationTime.INSERT)
    //@Column(name = "id", insertable = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GEN")
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "amount_left")
    private int amountLeft;
    @Basic(optional = false)
    @Column(name = "price")
    private BigDecimal price;
    //@Basic(optional = false)
    @Column(name = "from_date", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Generated(GenerationTime.INSERT)
    private Date fromDate; //при записи в базу переопределяется триггером
    @Column(name = "to_date", insertable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @Generated(GenerationTime.INSERT)
    private Date toDate; //при записи в базу переопределяется триггером.  требует refresh'а
    @JoinColumns({
        @JoinColumn(name = "product_id", referencedColumnName = "product_id"),
        @JoinColumn(name = "seller_id", referencedColumnName = "seller_id")})
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Supply supply;

    public SupplyChange() {
    }

//    public SupplyChange(Integer id) {
//        this.id = id;
//    }

    public SupplyChange(int amountLeft, BigDecimal price) {
        this.amountLeft = amountLeft;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAmountLeft() {
        return amountLeft;
    }

    public void setAmountLeft(int amountLeft) {
        this.amountLeft = amountLeft;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public Supply getSupply() {
        return supply;
    }

    public void setSupply(Supply supply) {
        this.supply = supply;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupplyChange)) {
            return false;
        }
        SupplyChange other = (SupplyChange) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.SupplyChange[id=" + id + "]";
    }

}
