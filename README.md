# Retail-store-bill-discounts
This project implements different kinds of discounts for a small retail website.

# Activities done:
- Clean and easy to understand code using Checkstyle

	- You can run the checkstyle using below command:
	
    	mvn checkstyle:checkstyle

- Code coverage using cobertura mvn plugin

	- Run the below  command for code coverage report:

		mvn cobertura:cobertura 

# Assumption Made:
1.Mutually exclusive discounts are applied first.
	i.e. discounts related to whether, user is Employee,Affiliate or Customer since more than two years.

2.As per assigment,Since we are talking about discounts on entire bill.
	Therefore to make case very simple ,I have assummed entire bill to be of one category.

3.While applying multiple discounts,Lets say for two discounts ,the second discount is applied on discounted amount.

4.The current system can enhanced to add more details in the system. like adding Product and product related data.
