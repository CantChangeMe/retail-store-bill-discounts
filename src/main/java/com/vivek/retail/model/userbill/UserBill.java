package com.vivek.retail.model.userbill;

import java.math.BigDecimal;
import java.util.List;

import com.vivek.retail.app.constants.Category;
import com.vivek.retail.model.discount.Discount;
import com.vivek.retail.model.user.User;



/**
 * @author c_vivek.kumar
 *
 *This class Bill has information about user ,net amount ,bill category and different kind
 *of discounts he is eligible for.
 */
public class UserBill{
    
	private User user;
    
    private BigDecimal net;
    
    private BigDecimal netPayable;
    
    private Category category;
    
    private List<Discount> alwaysApplicableDiscounts;
    
    private List<Discount> mutuallyExclusiveDiscounts;
    
    /**
     * Create a bill for user with net and category
     * @param user the user
     * @param net the net total 
     * @param category the category
     */
    public UserBill(User user, BigDecimal net, Category category) {
        super();
        this.user = user;
        this.net = net;
        this.category = category;
    }
    
    /**
     * @return it returns net payable
     */
    public BigDecimal applyDiscounts() {
        netPayable = net;
        
        BigDecimal discountAmount = null;
     
        if((mutuallyExclusiveDiscounts != null) && !mutuallyExclusiveDiscounts.isEmpty()) {
            
            for(Discount discount: mutuallyExclusiveDiscounts) {
                
                discountAmount = discount.calculateDiscountOnTheBill(this);
                
                if(discountAmount != null) {
                    break;
                }
            }
            
            if(discountAmount != null) {
                netPayable = netPayable.subtract(discountAmount);
            }
        }

        if((alwaysApplicableDiscounts != null) && !alwaysApplicableDiscounts.isEmpty()) {
            
            for(Discount discount: alwaysApplicableDiscounts) {
                
                discountAmount = discount.calculateDiscountOnTheBill(this);
                
                if(discountAmount != null) {
                    netPayable = netPayable.subtract(discountAmount);
                }
            }
        }
        
        return netPayable;
    }
    
    public BigDecimal getNetPayable() {
        return netPayable;
    }

    public void setNetPayable(BigDecimal netPayable) {
        this.netPayable = netPayable;
    }


    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getNet() {
        return net;
    }

    public void setNet(BigDecimal net) {
        this.net = net;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Discount> getAlwaysApplicable() {
        return alwaysApplicableDiscounts;
    }

    public void setAlwaysApplicable(List<Discount> alwaysApplicable) {
        this.alwaysApplicableDiscounts = alwaysApplicable;
    }

    public List<Discount> getMutuallyExclusive() {
        return mutuallyExclusiveDiscounts;
    }

    public void setMutuallyExclusive(List<Discount> mutuallyExclusive) {
        this.mutuallyExclusiveDiscounts = mutuallyExclusive;
    }

}
