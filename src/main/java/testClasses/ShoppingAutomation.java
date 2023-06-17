package testClasses;

import java.util.Set;

import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import baseClasses.BaseClass;
import pageClasses.CartPage;
import pageClasses.Homepage;
import pageClasses.ProductPage;
import pageClasses.SearchPage;
import utils.LocalTextFormatter;

public class ShoppingAutomation extends BaseClass {

	Homepage homepage;
	SearchPage searchPage;
	ProductPage productPage;
	CartPage cartPage;

	@Parameters("browser")
	@Test
	public void testCartOrderValue(String browser) {

		invokeBrowser(browser);
		homepage = openHomepage("https://www.flipkart.com/");
		verifyTitle(
				"Online Shopping Site for Mobiles, Electronics, Furniture, Grocery, Lifestyle, Books & More. Best Offers!");
		homepage.clickCancelModal();
		searchPage = homepage.searchProduct("Mobiles");
		String p = driver.getWindowHandle();
		productPage = searchPage.clickOnFirstNonSponsoredLink();

		String c = null;
		Set<String> sa = driver.getWindowHandles();
		for (String s : sa) {
			if (!s.equals(p)) {
				c = s;
				driver.switchTo().window(s);
			}
		}

		
		cartPage = productPage.clickAddToCartAndOpenCart();
		String costOfFirst = cartPage.getOrderValue();
		reportOnConsole("Price of first product - " + costOfFirst);
		String orderAmount = cartPage.getOrderValue();
		reportOnConsole("Order Amount is -" + orderAmount);
		takeScreenshot();
		tearDown();
		driver.switchTo().window(p);

		searchPage.clearSearchBox();
		searchPage = searchPage.searchProduct("Laptops");
		waitLoad(5);
		productPage = searchPage.clickOnFirstNonSponsoredLink();

		Set<String> sa2 = driver.getWindowHandles();
		for (String s : sa2) {
			if (!s.equals(p)) {
				c = s;
				driver.switchTo().window(s);
			}
		}

		String costOfSecond = productPage.getProductPrice();
		reportOnConsole("Price of second product - " + costOfSecond);
		cartPage = productPage.clickAddToCartAndOpenCart();
		String newOrderAmount = cartPage.getOrderValue();
		reportOnConsole("New Order Amount is -" + newOrderAmount);
		takeScreenshot();

		int c1 = LocalTextFormatter.formatPrice(costOfFirst);
		int c2 = LocalTextFormatter.formatPrice(costOfSecond);
		int orderTotal = LocalTextFormatter.formatPrice(newOrderAmount);

		try {
			Assert.assertEquals(c1 + c2, orderTotal, "Amount matched");
			reportOnConsole(c1 + c2 + " matches with " + orderTotal);

		} catch (Exception e) {
			System.err.println(c1 + c2 + "- Expected amount, Actual amount -" + orderTotal);
		}
		waitLoad(10);
	}

}
