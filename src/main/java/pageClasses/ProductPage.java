package pageClasses;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import baseClasses.BaseClass;

public class ProductPage extends BaseClass {

	public ProductPage(WebDriver driver) {
		this.driver = driver;
	}

	@FindBy(xpath = "//div[@class='CEmiEU']//div[contains(@class,'_30jeq3')]")
	public WebElement priceElement;

	@FindBy(xpath = "//button[text()='Add to cart']")
	public WebElement addCartBtn;

	@FindBy(className = "_3SkBxJ")
	public WebElement cartBtn;

	
	//returns the price of the product
	public String getProductPrice() {
		return priceElement.getText();
	}

	//clicks on add to card button and returns  CartPage
	public CartPage clickAddToCartAndOpenCart() {
		elementClick(addCartBtn);
		waitLoad(5);
		try {
			verifyTitle("Shopping Cart | Flipkart.com");
			return PageFactory.initElements(driver, CartPage.class);
		} catch (AssertionError e) {
			elementClick(cartBtn);
			waitLoad(5);
			return PageFactory.initElements(driver, CartPage.class);
		}
	}
}
