package com.vivek.retail.model.discount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;

import com.vivek.retail.app.constants.Category;
import com.vivek.retail.app.constants.DiscountType;
import com.vivek.retail.app.constants.UserType;
import com.vivek.retail.model.bill.Bill;
import com.vivek.retail.model.user.User;

public class CustomerPeriodDiscountTest {
    
    private Bill bill;

    private User user;

    private CustomerPeriodDiscount discount;

    @Before
    public void setUp() throws Exception {
        
        Date date = DateUtils.addYears(new Date(), -3);

        user = new User(date, UserType.EMPLOYEE);

        bill = new Bill(user, new BigDecimal(450), Category.GROCERIES);

        Set<Category> categoriesWithNoPercentageDiscountlude = new HashSet<>();
        categoriesWithNoPercentageDiscountlude.add(Category.GROCERIES);

        discount = 
                new CustomerPeriodDiscount(DiscountType.PERCENTAGE, new BigDecimal(5), categoriesWithNoPercentageDiscountlude, 24);
    }

    @Test
    public void testCustomerPeriodDiscountValid() {
        CustomerPeriodDiscount discount = 
                new CustomerPeriodDiscount(DiscountType.AMOUNT, new BigDecimal(5), null, 24);
        
        assertEquals(new Integer(24), discount.getCustomerSinceMonths());
        assertEquals(DiscountType.AMOUNT, discount.getType());
    }
    
    
    @Test(expected = IllegalArgumentException.class)
    public void testCustomerPeriodDiscountInvalidDiscount() {
         new CustomerPeriodDiscount(DiscountType.PERCENTAGE, null, null, 24);
    }
    
    @Test
    public void testCalculateApplicable() {
        bill.setNetPayable(bill.getNet());
        
        bill.setCategory(Category.ELECTRONICS);
        
        BigDecimal amount = discount.calculateDiscountOnTheBill(bill);
        assertNotNull(amount);
        
        assertEquals(new BigDecimal(22.50).setScale(2), amount);
        
        bill.setNetPayable(new BigDecimal(125.50));
        discount.setDiscount(new BigDecimal(10));
        amount = discount.calculateDiscountOnTheBill(bill);
        assertNotNull(amount);
        
        assertEquals(new BigDecimal(12.55).setScale(2, RoundingMode.HALF_UP), amount);
        
        
        discount.setType(DiscountType.AMOUNT);
        
        amount = discount.calculateDiscountOnTheBill(bill);
        
        assertEquals(new BigDecimal(10).setScale(2), amount);
        
    }
}
