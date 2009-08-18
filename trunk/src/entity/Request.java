/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
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
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

/**
 *
 * @author е
 */
@SequenceGenerator(
    name="SEQ_GEN",
    sequenceName="request_id_seq",
    allocationSize=1
)
@Entity
@Table(name = "request")
@NamedQueries({@NamedQuery(name = "Request.findAll", query = "SELECT r FROM Request r"),
@NamedQuery(name = "Request.findByReceiverIdSenderId", query = "SELECT i FROM Bill i" +
" WHERE i.sender = :SenderId AND i.receiver = :ReceiverId")})
public class Request implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GEN")
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "creation_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Basic(optional = false)
    @Column(name = "approved")
    private boolean approved;
    @JoinColumn(name = "receiver_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Customer receiver;
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Customer sender;
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Employee employee;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "request", fetch = FetchType.LAZY)
    private List<RequestProduct> requestProductCollection;

//    private BigDecimal requestSum;

    public Request() {
    }

    public Request(Integer id) {
        this.id = id;
    }

    public Request(Integer id, Date creationDate, boolean approved) {
        this.id = id;
        this.creationDate = creationDate;
        this.approved = approved;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public boolean getApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public Customer getReceiverId() {
        return receiver;
    }

    public void setReceiverId(Customer receiverId) {
        this.receiver = receiverId;
    }

    public Customer getSenderId() {
        return sender;
    }

    public void setSenderId(Customer senderId) {
        this.sender = senderId;
    }

    public Employee getEmployeeId() {
        return employee;
    }

    public void setEmployeeId(Employee employeeId) {
        this.employee = employeeId;
    }

    public List<RequestProduct> getRequestProductCollection() {
        return requestProductCollection;
    }

    public void setRequestProductCollection(List<RequestProduct> requestProductCollection) {
        this.requestProductCollection = requestProductCollection;
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
        if (!(object instanceof Request)) {
            return false;
        }
        Request other = (Request) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return '№' + this.id.toString() + " от " + DateFormat.getDateInstance(DateFormat.DATE_FIELD).format(creationDate)
                + ":  " + calcSum(this).toString() + "р.";
    }

    public static BigDecimal calcSum(Request i) {
        if (i == null || i.getRequestProductCollection() == null) return null;
        BigDecimal sum = new BigDecimal(0);
        for(RequestProduct ip : i.getRequestProductCollection()) {
            sum = sum.add(ip.getCost());
        }
        return sum;
    }
}
