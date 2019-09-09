package com.vivek.retail.model.discount;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;

import com.vivek.retail.app.constants.Category;
import com.vivek.retail.app.constants.DiscountType;
import com.vivek.retail.model.bill.Bill;

public abstract class GenericDiscount implements Discount {
    
    private DiscountType type;
    
    private BigDecimal discount;
    
    private Set<Category> categoriesWithNoPercentageDiscount;
    
    /**
     * @param type discount type
     * @param discount discount 
     * @param categoriesWithNoPercentageDiscount categories to exclude
     */
    public GenericDiscount(DiscountType type, BigDecimal discount, Set<Category> categoriesWithNoPercentageDiscount) {
        super();
        
        if(discount == null) {
            throw new IllegalArgumentException("discount is required");
        }
        
        this.type = type;
        this.discount = discount;
        this.categoriesWithNoPercentageDiscount = categoriesWithNoPercentageDiscount;
        
        // default to percentage
        if(this.type == null) {
            this.type = DiscountType.PERCENTAGE;
        }
        
    }

    protected boolean isCategoryApplicable(Category category) {
        return (categoriesWithNoPercentageDiscount == null) || (category == null) || !categoriesWithNoPercentageDiscount.contains(category) ;
    }
    
    protected void validate(Bill bill) {
        if((bill == null) || (bill.getNetPayable() == null)) {
            throw new IllegalArgumentException("discountable is missing or invalid");
        }
    }
    

    @Override
    public BigDecimal calculateDiscountOnTheBill(Bill bill) {
        BigDecimal amount = null;
        
        validate(bill);
        
        if(discount == null) {
            throw new IllegalArgumentException("discount is null");
        }
        
        if(this.isDiscountApplicableOnTheBill(bill)) {
            BigDecimal net = bill.getNetPayable();
            
            if(DiscountType.PERCENTAGE.equals(type)) {                
                amount = discount.divide(new BigDecimal(100.00), 2, RoundingMode.HALF_UP).multiply(net);
                
            } else if(DiscountType.AMOUNT.equals(type)) {                
                amount = discount;
            
            } else {
                throw new IllegalArgumentException("invalid discountType: " + type);
            }
            amount = amount.setScale(2, RoundingMode.HALF_UP);
            
        }
        
        return amount;
    }
 
    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Set<Category> getCategoriesWithNoPercentageDiscount() {
        return categoriesWithNoPercentageDiscount;
    }

    public void setCategoriesWithNoPercentageDiscounts(Set<Category> categoriesWithNoPercentageDiscount) {
        this.categoriesWithNoPercentageDiscount = categoriesWithNoPercentageDiscount;
    }

    public DiscountType getType() {
        return type;
    }

    public void setType(DiscountType type) {
        this.type = type;
    }
    
}
