package com.vivek.retail.model.discount;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Set;

import com.vivek.retail.app.constants.Category;
import com.vivek.retail.app.constants.DiscountType;
import com.vivek.retail.model.bill.Bill;

public class NetMultiplesDiscount extends GenericDiscount {
    
    private BigDecimal netMultiples;
    public NetMultiplesDiscount(BigDecimal discount, Set<Category> categoriesWithNoPercentageDiscount, 
            BigDecimal netMultiples) {
        
        super(DiscountType.AMOUNT, discount, categoriesWithNoPercentageDiscount);
        
        if((netMultiples == null) || BigDecimal.ZERO.equals(netMultiples)) {
            throw new IllegalArgumentException("netMultiples is missing or invalid");
        }
        
        this.netMultiples = netMultiples;
        
    }

    @Override
    public boolean isDiscountApplicableOnTheBill(Bill bill) {
        
        validate(bill);
        boolean applicable = super.isCategoryApplicable(bill.getCategory());
        
        if(applicable) {

            int compare = bill.getNetPayable().compareTo(netMultiples);
            applicable = (compare == 0) || (compare == 1);
        }         
        return applicable;
    }
    
    @Override
    public BigDecimal calculateDiscountOnTheBill(Bill bill) {
        BigDecimal amount = null;
        
        validate(bill);
       
        if(this.isDiscountApplicableOnTheBill(bill)) {
            
            amount = bill.getNetPayable().divide(netMultiples, 0, RoundingMode.FLOOR);
            
            amount = amount.multiply(getDiscount()).setScale(2, RoundingMode.HALF_UP);;

        }
        
        return amount;
    }
    
    public BigDecimal getNetMultiples() {
        return netMultiples;
    }

    public void setNetMultiples(BigDecimal netMultiples) {
        this.netMultiples = netMultiples;
    }


}
