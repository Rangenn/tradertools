/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.spi.DateFormatProvider;
import java.util.Date;
import javax.persistence.Basic;
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
    sequenceName="bill_id_seq",
    allocationSize=1
)
@Entity
@Table(name = "bill")
@NamedQueries({@NamedQuery(name = "Bill.findAll", query = "SELECT b FROM Bill b"),
@NamedQuery(name = "Bill.findByReceiverIdSenderId", query = "SELECT i FROM Bill i" +
" WHERE i.sender = :SenderId AND i.receiver = :ReceiverId")})
public class Bill implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GEN")
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "bill_sum")
    private BigDecimal billSum;
    @Basic(optional = false)
    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Column(name = "purpose")
    private String purpose;
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Customer sender;
    @JoinColumn(name = "receiver_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Customer receiver;

    public Bill() {
    }

//    public Bill(Integer id) {
//        this.id = id;
//    }

    public Bill(Integer id, BigDecimal sum, Date creationDate) {
        this.id = id;
        this.billSum = sum;
        this.creationDate = creationDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getBillSum() {
        return billSum;
    }

    public void setBillSum(BigDecimal money) {
        this.billSum = money;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public Customer getSenderId() {
        return sender;
    }

    public void setSenderId(Customer senderId) {
        this.sender = senderId;
    }

    public Customer getReceiverId() {
        return receiver;
    }

    public void setReceiverId(Customer receiverId) {
        this.receiver = receiverId;
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
        if (!(object instanceof Bill)) {
            return false;
        }
        Bill other = (Bill) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return '№' + this.id.toString() + " от " + DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(creationDate)
                + ":  " + this.billSum.toString() + "р.";
    }

}
