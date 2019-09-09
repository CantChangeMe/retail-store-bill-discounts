package com.vivek.retail.service.discount;

import java.util.List;

import com.vivek.retail.model.discount.Discount;

public interface DiscountProviderService {
    
    List<Discount> getAlwayApplicableDiscounts();
    List<Discount> getMutuallyExclusiveDiscounts();

}
