package com.vivek.retail.model.discount;

import java.math.BigDecimal;

import com.vivek.retail.model.userbill.UserBill;


public interface Discount {
    
    public BigDecimal calculateDiscountOnTheBill(UserBill bill);    
   
    public boolean isDiscountApplicableOnTheBill(UserBill bill);

}
