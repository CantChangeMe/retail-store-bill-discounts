package com.vivek.retail.model.discount;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import com.vivek.retail.app.constants.Category;
import com.vivek.retail.app.constants.DiscountType;
import com.vivek.retail.model.bill.Bill;

public class CustomerPeriodDiscount extends GenericDiscount {
    
    private Integer customerSinceMonths;
    
    public CustomerPeriodDiscount(DiscountType type, BigDecimal discount, Set<Category> categoriesWithNoPercentageDiscount,
            Integer customerSinceMonths) {
        
        super(type, discount, categoriesWithNoPercentageDiscount);
        this.customerSinceMonths = customerSinceMonths;
    }

    public boolean isDiscountApplicableOnTheBill(Bill bill) {
        
        if((bill == null) || (bill.getUser() == null) 
                || (bill.getUser().getCustomerSince() == null)) {
            throw new IllegalArgumentException("bill is missing or invalid");
        }
        
        if(customerSinceMonths == null) {
            throw new IllegalArgumentException("months is required");
        }
        
        boolean applicable = super.isCategoryApplicable(bill.getCategory());
        
        if(applicable) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -customerSinceMonths);
            // start of the day
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);

            Date customerSince = bill.getUser().getCustomerSince();

            applicable = customerSince.before(calendar.getTime());

        }

        return applicable;
    }

    public Integer getCustomerSinceMonths() {
        return customerSinceMonths;
    }

    public void setCustomerSinceMonths(Integer months) {
        this.customerSinceMonths = months;
    }

}
