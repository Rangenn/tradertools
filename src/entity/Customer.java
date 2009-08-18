/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author е
 */
@SequenceGenerator(
    name="SEQ_GEN",
    sequenceName="customer_id_seq",
    allocationSize=1
)
@Entity
@Table(name = "customer")
//@NamedQueries({@NamedQuery(name = "Customer.findAll", query = "SELECT c FROM Customer c"), @NamedQuery(name = "Customer.findById", query = "SELECT c FROM Customer c WHERE c.id = :id"), @NamedQuery(name = "Customer.findByTitle", query = "SELECT c FROM Customer c WHERE c.title = :title"), @NamedQuery(name = "Customer.findByIsSupplier", query = "SELECT c FROM Customer c WHERE c.isSupplier = :isSupplier"), @NamedQuery(name = "Customer.findByItn", query = "SELECT c FROM Customer c WHERE c.itn = :itn"), @NamedQuery(name = "Customer.findByAccount", query = "SELECT c FROM Customer c WHERE c.account = :account"), @NamedQuery(name = "Customer.findByAddress", query = "SELECT c FROM Customer c WHERE c.address = :address"), @NamedQuery(name = "Customer.findByPhone", query = "SELECT c FROM Customer c WHERE c.phone = :phone"), @NamedQuery(name = "Customer.findByEmail", query = "SELECT c FROM Customer c WHERE c.email = :email"), @NamedQuery(name = "Customer.findByCommentary", query = "SELECT c FROM Customer c WHERE c.commentary = :commentary")})
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GEN")
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "title")
    private String title;
    @Basic(optional = false)
    @Column(name = "is_supplier")
    private boolean isSupplier;
    @Column(name = "itn")
    private String itn;
    @Column(name = "account")
    private String account;
    @Column(name = "address")
    private String address;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "commentary")
    private String commentary;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sender", fetch = FetchType.LAZY)
    private List<Bill> billsMakedOutCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "receiver", fetch = FetchType.LAZY)
    private List<Bill> billsReceivedCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sender", fetch = FetchType.LAZY)
    private List<Request> requestsMakedOutCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "receiver", fetch = FetchType.LAZY)
    private List<Request> requestsReceivedCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "buyer", fetch = FetchType.LAZY)
    private List<Invoice> invoicesReceivedCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "seller", fetch = FetchType.LAZY)
    private List<Invoice> invoicesMakedOutCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer", fetch = FetchType.LAZY)
    private List<Supply> supplyCollection;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Customer() {
    }

//    public Customer(Integer id) {
//        this.id = id;
//    }

    public Customer(String title, boolean isSupplier) {
        this.title = title;
        this.isSupplier = isSupplier;
    }

    public Customer(String title, String ITN, String account, String Phone, String Email, String comment, boolean isSupplier) {
        this(title,isSupplier);
        this.itn = ITN;
        this.account = account;
        this.phone = Phone;
        this.email = Email;
        this.commentary = comment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean IsSupplier() {
        return isSupplier;
    }

    public void setIsSupplier(boolean isSupplier) {
        this.isSupplier = isSupplier;
    }

    public String getItn() {
        return itn;
    }

    public void setItn(String itn) {
        this.itn = itn;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public List<Bill> getBillsMakedOutCollection() {
        return billsMakedOutCollection;
    }

    public void setBillsMakedOutCollection(List<Bill> billCollection) {
        this.billsMakedOutCollection = billCollection;
    }

    public List<Bill> getBillsReceivedCollection() {
        return billsReceivedCollection;
    }

    public void setBillsReceivedCollection(List<Bill> billCollection) {
        this.billsReceivedCollection = billCollection;
    }

    public List<Invoice> getInvoicesReceivedCollection() {
        return invoicesReceivedCollection;
    }

    public void setInvoicesReceivedCollection(List<Invoice> invoiceCollection) {
        this.invoicesReceivedCollection = invoiceCollection;
    }

    public List<Invoice> getInvoicesMakedOutCollection() {
        return invoicesMakedOutCollection;
    }

    public void setInvoicesMakedOutCollection(List<Invoice> invoiceCollection) {
        this.invoicesMakedOutCollection = invoiceCollection;
    }

    public List<Supply> getSupplyCollection() {
        return supplyCollection;
    }

    public void setSupplyCollection(List<Supply> supplyCollection) {
        this.supplyCollection = supplyCollection;
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
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return getTitle();
    }

    /**
     * @return the requestsMakedOutCollection
     */
    public List<Request> getRequestsMakedOutCollection() {
        return requestsMakedOutCollection;
    }

    /**
     * @param requestsMakedOutCollection the requestsMakedOutCollection to set
     */
    public void setRequestsMakedOutCollection(List<Request> requestsMakedOutCollection) {
        this.requestsMakedOutCollection = requestsMakedOutCollection;
    }

    /**
     * @return the requestsReceivedCollection
     */
    public List<Request> getRequestsReceivedCollection() {
        return requestsReceivedCollection;
    }

    /**
     * @param requestsReceivedCollection the requestsReceivedCollection to set
     */
    public void setRequestsReceivedCollection(List<Request> requestsReceivedCollection) {
        this.requestsReceivedCollection = requestsReceivedCollection;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    public void sortSupplies() {
        if (getSupplyCollection() != null) {
            Collections.sort(getSupplyCollection(), new util.SupplyComparator());
        }
    }

}
