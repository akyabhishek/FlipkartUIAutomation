package pageClasses;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import baseClasses.BaseClass;

public class Homepage extends BaseClass {

	public Homepage(WebDriver driver) {
		this.driver = driver;
	}

	@FindBy(xpath = "//button[contains(@class,\"_2doB4z\")]")
	public WebElement regModal;

	@FindBy(xpath = "//button[contains(@class,\"_2doB4z\")]")
	public WebElement regModalCancelBtn;

	@FindBy(name = "q")
	public WebElement searchBox;

	@FindBy(className = "L0Z3Pu")
	public WebElement searchBtn;

	
	//search product and returns the searchpage
	public SearchPage searchProduct(String searchStr) {
		enterText(searchBox, searchStr);
		elementClick(searchBtn);
		return PageFactory.initElements(driver, SearchPage.class);
	}

	//clicks on cancel if register modal is visible
	public void clickCancelModal() {
		try {
			regModal.isDisplayed();
			elementClick(regModalCancelBtn);
		} catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.println("Registration card is not shown.");
		}
	}

}
