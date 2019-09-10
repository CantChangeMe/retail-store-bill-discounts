package com.vivek.retail.service.discount;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.vivek.retail.app.constants.Category;
import com.vivek.retail.app.constants.DiscountType;
import com.vivek.retail.app.constants.UserType;
import com.vivek.retail.model.discount.CustomerLoyaltyDiscount;
import com.vivek.retail.model.discount.Discount;
import com.vivek.retail.model.discount.NetMultiplesBasedDiscount;
import com.vivek.retail.model.discount.UserTypeBasedDiscount;


public class DiscountProviderServiceImpl implements DiscountProviderService {
    
    private List<Discount> alwaysApplicableDiscountsList = new ArrayList<>();    
    private List<Discount> mutuallyExclusiveDiscountsList = new ArrayList<>();
    
    /**
     *  Create service for the system which includes different kind of discounts it provides
     */
    public DiscountProviderServiceImpl() {
        super();
        
        alwaysApplicableDiscountsList = new ArrayList<>();
        Discount discount = new NetMultiplesBasedDiscount(new BigDecimal(5), null, 
                new BigDecimal(100));
        
        alwaysApplicableDiscountsList.add(discount);
        Set<Category> categoriesWithNoPercentageDiscount = new HashSet<>();
        categoriesWithNoPercentageDiscount.add(Category.GROCERIES);
        
        
        discount = new UserTypeBasedDiscount(DiscountType.PERCENTAGE, 
                new BigDecimal(30), categoriesWithNoPercentageDiscount, UserType.EMPLOYEE);
        mutuallyExclusiveDiscountsList.add(discount);
        
        discount = new UserTypeBasedDiscount(DiscountType.PERCENTAGE, 
                new BigDecimal(10), categoriesWithNoPercentageDiscount, UserType.AFFILIATE);
        mutuallyExclusiveDiscountsList.add(discount);
        
        discount = new CustomerLoyaltyDiscount(DiscountType.PERCENTAGE, 
                new BigDecimal(5), categoriesWithNoPercentageDiscount, 24);
        mutuallyExclusiveDiscountsList.add(discount);        
    }

    @Override
    public List<Discount> getAlwayApplicableDiscounts() {
        
        return Collections.unmodifiableList(alwaysApplicableDiscountsList);
    }

    @Override
    public List<Discount> getMutuallyExclusiveDiscounts() {
        
        return Collections.unmodifiableList(mutuallyExclusiveDiscountsList);
    }

}
