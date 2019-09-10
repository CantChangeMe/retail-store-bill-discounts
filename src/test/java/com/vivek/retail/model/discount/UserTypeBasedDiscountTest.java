package com.vivek.retail.model.discount;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.vivek.retail.app.constants.Category;
import com.vivek.retail.app.constants.DiscountType;
import com.vivek.retail.app.constants.UserType;
import com.vivek.retail.model.discount.UserTypeBasedDiscount;
import com.vivek.retail.model.user.User;
import com.vivek.retail.model.userbill.UserBill;

public class UserTypeBasedDiscountTest {
    
    private UserBill bill;
    
    private User user;
    
    private UserTypeBasedDiscount discount;

    @Before
    public void setUp() throws Exception {
        
        user = new User(new Date(), UserType.EMPLOYEE);
        
        bill = new UserBill(user, new BigDecimal(450), Category.GROCERIES);
        
        Set<Category> exclude = new HashSet<>();
        exclude.add(Category.GROCERIES);
        
        discount = 
                new UserTypeBasedDiscount(DiscountType.PERCENTAGE, new BigDecimal(30), exclude, UserType.EMPLOYEE);
    }

    @Test
    public void testUserTypeDiscountValid() {
        UserTypeBasedDiscount discount = 
                new UserTypeBasedDiscount(DiscountType.AMOUNT, new BigDecimal(10), null, UserType.AFFILIATE);
        
        assertEquals(UserType.AFFILIATE, discount.getUserType());
        assertEquals(DiscountType.AMOUNT, discount.getType());
    }
    
    @Test
    public void testUserTypeDiscountDefaultType() {
        UserTypeBasedDiscount discount = 
                new UserTypeBasedDiscount(null, new BigDecimal(10), null, UserType.CUSTOMER);
        
        assertEquals(UserType.CUSTOMER, discount.getUserType());
        assertEquals(DiscountType.PERCENTAGE, discount.getType());
    }
    
    
       
    @Test
    public void testCalculateNotApplicable() {
        bill.setNetPayable(new BigDecimal(220));
        assertNull(discount.calculateDiscountOnTheBill(bill));

        discount.setUserType(UserType.AFFILIATE);
        assertNull(discount.calculateDiscountOnTheBill(bill));
    }  
}
