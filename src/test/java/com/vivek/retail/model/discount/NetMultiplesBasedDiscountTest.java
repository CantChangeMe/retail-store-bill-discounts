package com.vivek.retail.model.discount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
import com.vivek.retail.model.discount.NetMultiplesBasedDiscount;
import com.vivek.retail.model.user.User;
import com.vivek.retail.model.userbill.UserBill;

public class NetMultiplesBasedDiscountTest {

    private UserBill bill;

    private User user;

    private NetMultiplesBasedDiscount discount;

    @Before
    public void setUp() throws Exception {

        Date date = DateUtils.addYears(new Date(), -3);

        user = new User(date, UserType.EMPLOYEE);

        bill = new UserBill(user, new BigDecimal(450), Category.GROCERIES);

        Set<Category> exclude = new HashSet<>();
        exclude.add(Category.GROCERIES);

        discount = 
                new NetMultiplesBasedDiscount(new BigDecimal(5), exclude, new BigDecimal(100));
    }

    @Test
    public void testNetMultiplesDiscountValid() {
        NetMultiplesBasedDiscount discount = 
                new NetMultiplesBasedDiscount(new BigDecimal(5), null, new BigDecimal(100));

        assertEquals(new BigDecimal(100), discount.getNetMultiples());
        assertEquals(DiscountType.AMOUNT, discount.getType());
    }


    @Test(expected = IllegalArgumentException.class)
    public void testNetMultiplesDiscountInvalidDiscount() {
        new NetMultiplesBasedDiscount(null, null, new BigDecimal(100));
    }


    @Test(expected = IllegalArgumentException.class)
    public void testNetMultiplesDiscountNullNetMultiples() {
        new NetMultiplesBasedDiscount(new BigDecimal(5), null, null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testNetMultiplesDiscountZeroNetMultiples() {
        new NetMultiplesBasedDiscount(new BigDecimal(5), null, BigDecimal.ZERO);
    }


    @Test
    public void testIsApplicableInvalidDiscountable() {
        try {
            discount.isDiscountApplicableOnTheBill(null);
            fail("expected exception not thrown");
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
        }

        bill.setNetPayable(null);
       
        try {
            discount.isDiscountApplicableOnTheBill(bill);
            fail("expected exception not thrown");
        } catch(IllegalArgumentException e) {
            e.printStackTrace();
        }

    }
    
    @Test
    public void testIsApplicable() {
        bill.setCategory(Category.CLOTHING);
        bill.setNetPayable(new BigDecimal(99));
        assertFalse(discount.isDiscountApplicableOnTheBill(bill));

        bill.setNetPayable(new BigDecimal(100));
        assertTrue(discount.isDiscountApplicableOnTheBill(bill));
        
        bill.setNetPayable(new BigDecimal(210));
        discount.setNetMultiples(new BigDecimal(200));
        assertTrue(discount.isDiscountApplicableOnTheBill(bill));

        bill.setCategory(null);
        assertTrue(discount.isDiscountApplicableOnTheBill(bill));

        discount.setCategoriesWithNoPercentageDiscounts(null);
        assertTrue(discount.isDiscountApplicableOnTheBill(bill));
        
    }

    @Test
    public void testCalculateNotApplicable() {
        bill.setNetPayable(new BigDecimal(220));
        // category excluded
        assertNull(discount.calculateDiscountOnTheBill(bill));

        // setting the customer period to 3 years
        discount.setNetMultiples(new BigDecimal(300));
        assertNull(discount.calculateDiscountOnTheBill(bill));
    }

    @Test
    public void testCalculateApplicable() {
        bill.setNetPayable(bill.getNet());

        bill.setCategory(Category.ELECTRONICS);

        BigDecimal amount = discount.calculateDiscountOnTheBill(bill);
        assertNotNull(amount);

        assertEquals(new BigDecimal(20.00).setScale(2), amount);
        bill.setNetPayable(new BigDecimal(990));
        discount.setNetMultiples(new BigDecimal(200));
        discount.setDiscount(new BigDecimal(10));
        amount = discount.calculateDiscountOnTheBill(bill);
        assertNotNull(amount);

        assertEquals(new BigDecimal(40.00).setScale(2, RoundingMode.HALF_UP), amount);

    }


}
