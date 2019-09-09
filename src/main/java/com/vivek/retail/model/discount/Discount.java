package com.vivek.retail.model.discount;

import java.math.BigDecimal;

import com.vivek.retail.model.bill.Bill;


public interface Discount {
    
    public BigDecimal calculateDiscountOnTheBill(Bill bill);    
    public boolean isDiscountApplicableOnTheBill(Bill bill);

}
