package pageClasses;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import baseClasses.BaseClass;

public class SearchPage extends BaseClass {

	public SearchPage(WebDriver driver) {
		this.driver = driver;
	}

	@FindBy(name = "q")
	public WebElement searchBox;

	@FindBy(className = "L0Z3Pu")
	public WebElement searchBtn;

	@FindAll(value = { @FindBy(className = "_2kHMtA") })
	public List<WebElement> cards;

	public SearchPage searchProduct(String searchStr) {
		enterText(searchBox, searchStr);
		elementClick(searchBtn);
		return PageFactory.initElements(driver, SearchPage.class);
	}

	public void clearSearchBox() {
		clearInput(searchBox);

	}

	public ProductPage clickOnFirstNonSponsoredLink() {
		for (int i = 0; i < cards.size(); i++) {
			List<WebElement> eList = cards.get(i).findElements(By.className("_2tfzpE"));
			if (eList.size() == 0) {
				cards.get(i).findElement(By.className("_1fQZEK")).click();
				return PageFactory.initElements(driver, ProductPage.class);
			}
		}
		return null;
	}
}
