package com.vivek.retail.model.discount;

import java.math.BigDecimal;
import java.util.Set;

import com.vivek.retail.app.constants.Category;
import com.vivek.retail.app.constants.DiscountType;
import com.vivek.retail.app.constants.UserType;
import com.vivek.retail.model.userbill.UserBill;

public class UserTypeBasedDiscount extends GenericDiscount {
    
    private UserType userType;
    
    /**
     * @param type
     * @param discount
     * @param categoriesWithNoPercentageDiscount
     * @param userType
     */
    public UserTypeBasedDiscount(DiscountType type, BigDecimal discount, Set<Category> categoriesWithNoPercentageDiscount, UserType userType) {
        super(type, discount, categoriesWithNoPercentageDiscount);
        if(userType == null) {
            throw new IllegalArgumentException("userType is required");
        }
        this.userType = userType;
    }

    @Override
    public boolean isDiscountApplicableOnTheBill(UserBill bill) {
        
        if((bill == null) || (bill.getUser() == null) 
                || (bill.getUser().getType() == null)) {
            
            throw new IllegalArgumentException("discountable is missing or invalid");
        }
        
        if(userType == null) {
            throw new IllegalArgumentException("userType is required");
        }
        
        boolean applicable = super.isCategoryApplicable(bill.getCategory());
        
        if(applicable) {    
            applicable = userType.equals(bill.getUser().getType());
        }
        
        return applicable;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

}
