package pageClasses;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import baseClasses.BaseClass;

public class CartPage extends BaseClass {

	public CartPage(WebDriver driver) {
		this.driver = driver;
	}

	@FindBy(xpath = "//div[@class='_1dqRvU']//span")
	public WebElement orderValuElement;

	// returns string value of order in rupees 
	public String getOrderValue() {
		return orderValuElement.getText();
	}
}
