/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

/**
 *
 * @author е
 */
@SequenceGenerator(
    name="SEQ_GEN",
    sequenceName="request_product_id_seq",
    allocationSize=1
)
@Entity
@Table(name = "request_product")
@NamedQueries({@NamedQuery(name = "RequestProduct.findAll", query = "SELECT r FROM RequestProduct r")})
public class RequestProduct implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEQ_GEN")
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "amount")
    private int amount;
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Product product;
    @JoinColumn(name = "request_id", referencedColumnName = "id")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Request request;


    private BigDecimal price;
    private BigDecimal cost;
    //для расчета предвариетльной суммы заказа. сумма счета, выставленного по заказу,
    //может отличаться в связи с постоянным изменением цен
    //и индивидуальными скидками клиента
    public RequestProduct() {
    }

    public RequestProduct(Integer id) {
        this.id = id;
    }

    public RequestProduct(Integer id, int amount) {
        this.id = id;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Product getProductId() {
        return product;
    }

    public void setProductId(Product productId) {
        this.product = productId;
    }

    public Request getRequestId() {
        return request;
    }

    public void setRequestId(Request requestId) {
        this.request = requestId;
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
        if (!(object instanceof RequestProduct)) {
            return false;
        }
        RequestProduct other = (RequestProduct) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.RequestProduct[id=" + id + "]";
    }

    /**
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * @return the cost
     */
    public BigDecimal getCost() {
        return cost;
    }

    /**
     * @param cost the cost to set
     */
    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public static BigDecimal calcCost(RequestProduct ip) {
        return ip.getPrice().multiply(new BigDecimal(ip.getAmount()));
    }
}
