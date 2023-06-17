package baseClasses;

import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;

import pageClasses.Homepage;
import utils.DateUtils;


public class BaseClass {

	public WebDriver driver;

	/********************* Invoke browser ***************************/
	public void invokeBrowser(String browserName) {
		try {
			if (browserName.equalsIgnoreCase("Chrome")) {
				System.setProperty("webdriver.chrome.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\chromedriver.exe");
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--remote-allow-origins=*");
				driver = new ChromeDriver(options);
			} else if (browserName.equalsIgnoreCase("Mozila")) {
				System.setProperty("webdriver.gecko.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\geckodriver.exe");
				FirefoxOptions options = new FirefoxOptions();
				options.addArguments("--remote-allow-origins=*");
				driver = new FirefoxDriver(options);
			} else if (browserName.equalsIgnoreCase("Edge")) {
				System.setProperty("webdriver.ie.driver",
						System.getProperty("user.dir") + "\\src\\test\\resources\\drivers\\msedgedriver.exe");
				EdgeOptions options = new EdgeOptions();
				options.addArguments("--remote-allow-origins=*");
				driver = new EdgeDriver(options);
			} else {
					reportFail(browserName+" is not a valid browser name");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
//			reportFail(e.getMessage());
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));

	}

	// opens URL
	public Homepage openHomepage(String URL) {
		try {
			driver.get(URL);
			return PageFactory.initElements(driver, Homepage.class);

		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		return null;
	}

	// closes the browser
	public void tearDown() {
		driver.close();

	}

	// quits the browser
	@AfterMethod
	public void quitBrowser() {
		driver.quit();
	}

	/****************** Waiting functions ***********************/
	public void waitForPageLoad() {
		JavascriptExecutor js = (JavascriptExecutor) driver;

		int i = 0;
		while (i != 180) {
			String pageState = (String) js.executeScript("return document.readyState;");
			if (pageState.equals("complete")) {
				break;
			} else {
				waitLoad(1);
			}
		}

		waitLoad(2);

		i = 0;
		while (i != 180) {
			Boolean jsState = (Boolean) js.executeScript("return window.jQuery != undefined && jQuery.active == 0;");
			if (jsState) {
				break;
			} else {
				waitLoad(1);
			}
		}
	}

	
	public void waitLoad(int i) {
		try {
			Thread.sleep(i * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/********************* Reporting functions ***************************/
	public void reportOnConsole(String reportString) {
		System.out.println(reportString);
	}
	public void reportFail(String reportString) {
		takeScreenshot();
		System.err.println(reportString);
		Assert.fail(reportString);
	}

	/********************** Takes screenshot *********************/
	public void takeScreenshot() {
		TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
		File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
		String filePath = System.getProperty("user.dir") + "//Screenshots//" + DateUtils.getTimeStamp()+ ".png";
		File destPath = new File(filePath);
		try {
			FileUtils.copyFile(sourceFile, destPath);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	/****************** Get Page Title ***********************/
	public void verifyTitle(String expectedTitle) {
		try {
			Assert.assertEquals(driver.getTitle(), expectedTitle);
			reportOnConsole("Actual Title : " + driver.getTitle() + " - equals to Expected Title : " + expectedTitle);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}

	}

	/****************** Enters text in element ***********************/
	public void enterText(WebElement element, String data) {
		try {
			element.sendKeys(data);
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	/****************** Click on any element ***********************/
	public void elementClick(WebElement element) {
		try {
			element.click();
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}
	/****************** Clear the input field ***********************/
	public void clearInput(WebElement element) {
		try {
		String s1 = Keys.chord(Keys.CONTROL, "a");
		element.sendKeys(s1);
		element.sendKeys(Keys.DELETE);
		}catch (Exception e) {
			reportFail(e.getMessage());
		}
	}



}
