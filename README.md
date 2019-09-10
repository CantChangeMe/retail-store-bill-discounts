# Retail-store-bill-discounts
This project implements different kinds of discounts for a small retail website.

# Activities done:
- Clean and easy to understand code using Checkstyle:
	I have tried to follow Google style guide for Java from below location:
	 https://google.github.io/styleguide/javaguide.html
	
	- You can run the checkstyle using below command:
	
    	mvn checkstyle:checkstyle
	- I have tried to change some style guides to suit my requirement of code readability and consistency within the code base.

- Code coverage using cobertura mvn plugin:

  Status report will be generated on location. "/retails-store-bill-discounts/target/site/cobertura/index.html"
	

	- Run the below  command for code coverage report:

		mvn cobertura:cobertura 
	- I have been able to generate code coverage of upto 93% as of now.
- Class diagram

![Class Diagram](https://github.com/CantChangeMe/retail-store-bill-discounts/blob/master/class-diagram/ClassDiagram.JPG)


# Assumption Made:
1.Mutually exclusive discounts are applied first.
	i.e. discounts related to whether, user is Employee,Affiliate or Customer since more than two years.

2.As per assigment,Since we are talking about discounts on entire bill.
	Therefore to make case very simple ,I have assummed entire bill to be of one category.

3.While applying multiple discounts,Lets say for two discounts ,the second discount is applied on discounted amount.

4.The current system can be enhanced to add more details in the system. like adding Product and product related data.
