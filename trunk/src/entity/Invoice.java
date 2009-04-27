/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author е
 */
@SequenceGenerator(
    name="SEQ_GEN",
    sequenceName="invoice_id_seq",
    allocationSize=1
)
@Entity
@Table(name = "invoice")
@NamedQueries({@NamedQuery(name = "Invoice.findAll", query = "SELECT i FROM Invoice i"),
@NamedQuery(name = "Invoice.findBySellerIdBuyerId", query = "SELECT i FROM Invoice i" +
" WHERE i.seller = :SellerId AND i.buyer = :BuyerId")})

public class Invoice implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GEN")
    @Column(name = "id")
    private Integer id;
    @Column(name = "invoice_sum")
    private BigDecimal invoiceSum;
    @Basic(optional = false)
    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Basic(optional = false)
    @Column(name = "paid")
    private boolean paid;
    @Basic(optional = false)
    @Column(name = "ready")
    private boolean ready;
    @Basic(optional = false)
    @Column(name = "shipped")
    private boolean shipped;
    @JoinColumn(name = "buyer_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Customer buyer;
    @JoinColumn(name = "seller_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Customer seller;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "invoice", fetch = FetchType.LAZY)
    private List<InvoiceProduct> invoiceProductCollection;

    public Invoice() {
    }

//    public Invoice(Integer id) {
//        this.id = id;
//    }

//    public Invoice(Integer id, boolean paid, boolean ready, boolean shipped) {
//        this.id = id;
//        this.paid = paid;
//        this.ready = ready;
//        this.shipped = shipped;
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getInvoiceSum() {
        return invoiceSum;
    }

    public void setInvoiceSum(BigDecimal invoiceSum) {
        this.invoiceSum = invoiceSum;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public boolean getPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public boolean getReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public boolean getShipped() {
        return shipped;
    }

    public void setShipped(boolean shipped) {
        this.shipped = shipped;
    }

    public Customer getBuyerId() {
        return buyer;
    }

    public void setBuyerId(Customer buyerId) {
        this.buyer = buyerId;
    }

    public Customer getSellerId() {
        return seller;
    }

    public void setSellerId(Customer sellerId) {
        this.seller = sellerId;
    }

    public List<InvoiceProduct> getInvoiceProductCollection() {
        return invoiceProductCollection;
    }

    public void setInvoiceProductCollection(List<InvoiceProduct> invoiceProductCollection) {
        this.invoiceProductCollection = invoiceProductCollection;
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
        if (!(object instanceof Invoice)) {
            return false;
        }
        Invoice other = (Invoice) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return '№' + this.id.toString() + " от " + DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(creationDate)
                + ":  " + this.invoiceSum.toString() + "р.";
    }

}
