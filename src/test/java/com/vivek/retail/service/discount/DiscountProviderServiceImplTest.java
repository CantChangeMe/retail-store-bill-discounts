package com.vivek.retail.service.discount;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.vivek.retail.app.constants.Category;
import com.vivek.retail.app.constants.DiscountType;
import com.vivek.retail.app.constants.UserType;
import com.vivek.retail.model.discount.CustomerLoyaltyDiscount;
import com.vivek.retail.model.discount.Discount;
import com.vivek.retail.model.discount.GenericDiscount;
import com.vivek.retail.model.discount.NetMultiplesBasedDiscount;
import com.vivek.retail.model.discount.UserTypeBasedDiscount;
import com.vivek.retail.service.discount.DiscountProviderService;
import com.vivek.retail.service.discount.DiscountProviderServiceImpl;

public class DiscountProviderServiceImplTest {
    
    private DiscountProviderService service;

    @Before
    public void setUp() throws Exception {
        
        service = new DiscountProviderServiceImpl();
    }
    
    @Test
    public void testLoadAlwayApplicableDiscounts() {
        
        List<Discount> discounts = service.getAlwayApplicableDiscounts();
        
        assertNotNull(discounts);
        
        assertEquals(1, discounts.size());
        
        NetMultiplesBasedDiscount discount = (NetMultiplesBasedDiscount) discounts.get(0);
        
        validateDiscount(discount, new BigDecimal(5), DiscountType.AMOUNT, true);
        
        assertEquals(new BigDecimal(100), discount.getNetMultiples());
    }

    @Test
    public void testLoadMutuallyExclusiveDiscounts() {
        List<Discount> discounts = service.getMutuallyExclusiveDiscounts();

        assertNotNull(discounts);

        assertEquals(3, discounts.size());
        
        UserTypeBasedDiscount discount = (UserTypeBasedDiscount) discounts.get(0);
        
        validateDiscount(discount, new BigDecimal(30), DiscountType.PERCENTAGE, false);
                
        assertEquals(UserType.EMPLOYEE, discount.getUserType());
        
        Set<Category> categoriesWithNoPercentageDiscountlude = discount.getCategoriesWithNoPercentageDiscount();        
        validateExclude(categoriesWithNoPercentageDiscountlude, Category.GROCERIES);
        
        discount = (UserTypeBasedDiscount) discounts.get(1);
        
        validateDiscount(discount, new BigDecimal(10), DiscountType.PERCENTAGE, false);
                
        assertEquals(UserType.AFFILIATE, discount.getUserType());
        
        categoriesWithNoPercentageDiscountlude = discount.getCategoriesWithNoPercentageDiscount();        
        validateExclude(categoriesWithNoPercentageDiscountlude, Category.GROCERIES);
        
        
        CustomerLoyaltyDiscount periodDiscount = (CustomerLoyaltyDiscount) discounts.get(2);
        
        validateDiscount(periodDiscount, new BigDecimal(5), DiscountType.PERCENTAGE, false);
                
        assertEquals(new Integer(24), periodDiscount.getCustomerSinceMonths());
        
        categoriesWithNoPercentageDiscountlude = periodDiscount.getCategoriesWithNoPercentageDiscount();        
        validateExclude(categoriesWithNoPercentageDiscountlude, Category.GROCERIES);
    }
    
    private void validateDiscount(GenericDiscount discount, BigDecimal amount, 
            DiscountType type, boolean emptyExclude) {
        
        assertNotNull(discount);
        
        assertEquals(amount, discount.getDiscount());
        
        if(emptyExclude) {
            assertNull(discount.getCategoriesWithNoPercentageDiscount());
        } else {
            assertNotNull(discount.getCategoriesWithNoPercentageDiscount());
        }
        
        assertEquals(type, discount.getType());
    }
    
    private void validateExclude(Set<Category> categoriesWithNoPercentageDiscountlude, Category... categories) {
        assertNotNull(categoriesWithNoPercentageDiscountlude);
        assertEquals(categories.length, categoriesWithNoPercentageDiscountlude.size());
        
        for(Category category: categories) {
            assertTrue(categoriesWithNoPercentageDiscountlude.contains(category));
        }
    }

}
