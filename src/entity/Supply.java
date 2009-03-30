/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author е
 */

@Entity
@Table(name = "supply")
@NamedQueries({@NamedQuery(name = "Supply.findAll", query = "SELECT s FROM Supply s")})
public class Supply implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected SupplyPK supplyPK;
    @Column(name = "title_alias")
    private String titleAlias;
    @Basic(optional = false)
    @Column(name = "amount_min")
    private Integer amountMin;
    @Column(name = "prev_price")
    private BigDecimal prevPrice;
    @Basic(optional = false)
    @Column(name = "actual_price")
    private BigDecimal actualPrice;
    @Column(name = "amount_left")
    private Integer amountLeft;
    @JoinColumn(name = "seller_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Customer customer;
    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Product product;

    public Supply() {
    }

    public Supply(SupplyPK supplyPK) {
        this.supplyPK = supplyPK;
    }

    public Supply(SupplyPK supplyPK, int amountMin, BigDecimal actualPrice) {
        this.supplyPK = supplyPK;
        this.amountMin = amountMin;
        this.actualPrice = actualPrice;
    }

    public Supply(int productId, int sellerId) {
        this.supplyPK = new SupplyPK(productId, sellerId);
    }

    public SupplyPK getSupplyPK() {
        return supplyPK;
    }

    public void setSupplyPK(SupplyPK supplyPK) {
        this.supplyPK = supplyPK;
    }

    public String getTitleAlias() {
        return titleAlias;
    }

    public void setTitleAlias(String titleAlias) {
        this.titleAlias = titleAlias;
    }

    public Integer getAmountMin() {
        return amountMin;
    }

    public void setAmountMin(Integer amountMin) {
        this.amountMin = amountMin;
    }

    public BigDecimal getPrevPrice() {
        return prevPrice;
    }

    public void setPrevPrice(BigDecimal prevPrice) {
        this.prevPrice = prevPrice;
    }

    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    public Integer getAmountLeft() {
        return amountLeft;
    }

    public void setAmountLeft(Integer amountLeft) {
        this.amountLeft = amountLeft;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (supplyPK != null ? supplyPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Supply)) {
            return false;
        }
        Supply other = (Supply) object;
        if ((this.supplyPK == null && other.supplyPK != null) || (this.supplyPK != null && !this.supplyPK.equals(other.supplyPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Supply[supplyPK=" + supplyPK + "]";
    }

}
