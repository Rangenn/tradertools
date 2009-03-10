/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author е
 */
@Embeddable
public class SupplyPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "product_id")
    private int productId;
    @Basic(optional = false)
    @Column(name = "seller_id")
    private int sellerId;

    public SupplyPK() {
    }

    public SupplyPK(int productId, int sellerId) {
        this.productId = productId;
        this.sellerId = sellerId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) productId;
        hash += (int) sellerId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SupplyPK)) {
            return false;
        }
        SupplyPK other = (SupplyPK) object;
        if (this.productId != other.productId) {
            return false;
        }
        if (this.sellerId != other.sellerId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.SupplyPK[productId=" + productId + ", sellerId=" + sellerId + "]";
    }

}
