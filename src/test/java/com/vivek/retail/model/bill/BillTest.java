package com.vivek.retail.model.bill;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;

import com.vivek.retail.app.constants.Category;
import com.vivek.retail.app.constants.UserType;
import com.vivek.retail.model.user.User;
import com.vivek.retail.service.discount.DiscountProviderService;
import com.vivek.retail.service.discount.DiscountProviderServiceImpl;

public class BillTest {

	private DiscountProviderService discountProviderService;

	private Bill bill;

	private User customer;

	@Before
	public void setUp() throws Exception {

		discountProviderService = new DiscountProviderServiceImpl();
		
		Date date = DateUtils.addYears(new Date(), -3);
		customer = new User(date, UserType.EMPLOYEE);
		
		bill = new Bill(customer, new BigDecimal(450), Category.GROCERIES);
		
		bill.setAlwaysApplicable(discountProviderService.getAlwayApplicableDiscounts());
		bill.setMutuallyExclusive(discountProviderService.getMutuallyExclusiveDiscounts());

	}

	@Test
	public void testApplyDiscountsNonApplicable() {

		customer.setType(UserType.CUSTOMER);
		customer.setCustomerSince(DateUtils.addYears(new Date(), -1));

		BigDecimal net = new BigDecimal(99.99);
		bill.setCategory(Category.CLOTHING);
		bill.setNet(net);

		BigDecimal netPayable = bill.applyDiscounts();
		System.out.println("Testing testApplyDiscountsNonApplicable:");
		System.out.println("net =" + net);
		System.out.println("netPayable =" + netPayable);
		assertEquals(net, netPayable);

	}

	@Test
	public void testApplyDiscountsEmployee() {
		bill.setCategory(Category.CLOTHING);
		bill.setNet(new BigDecimal(1450.00));

		BigDecimal netPayable = bill.applyDiscounts();
		assertEquals((new BigDecimal(965.00)).setScale(2), netPayable);
	}

	@Test
	public void testApplyDiscountsAffiliate() {
		customer.setType(UserType.AFFILIATE);
		bill.setCategory(Category.CLOTHING);
		bill.setNet(new BigDecimal(1450.00));

		BigDecimal netPayable = bill.applyDiscounts();
		assertEquals((new BigDecimal(1240.00)).setScale(2), netPayable);
	}

}
