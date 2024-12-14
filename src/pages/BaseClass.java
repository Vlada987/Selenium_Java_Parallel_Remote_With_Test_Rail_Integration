package pages;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.javafaker.Faker;

public class BaseClass {

	public WebDriver driver;
	public WebDriverWait wait;
	public Faker faker = new Faker();

	public BaseClass(WebDriver driver) {
		this.driver = driver;
		wait = new WebDriverWait(driver, Duration.ofSeconds(8));
	}

	public void waiting(By by) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	public void navigate(String url) {
		driver.navigate().to(url);
	}

	public void openPage(String url) {
		driver.get(url);
	};

	public Boolean isElementPresent(By by) {
		waiting(by);
		return driver.findElement(by).isDisplayed();
	}

	public String getText(By by) {
		waiting(by);
		return driver.findElement(by).getText();
	}

	public String getAtributte(By by, String atributte) {
		waiting(by);
		return driver.findElement(by).getAttribute(atributte);
	}

	public String getFromPage(String what) {
		if (what.equals("url")) {
			return driver.getCurrentUrl();
		} else {
			return driver.getTitle();
		}
	}

	public void enterText(By by, String text) {
		waiting(by);
		driver.findElement(by).sendKeys(text);
		driver.findElement(by).sendKeys(Keys.ENTER);
	}

	public void clickOn(By by) {
		waiting(by);
		driver.findElement(by).click();
	}

	public int checkLinkStatus(String href) {
		int statusCode = -1;
		try {
			URL url = new URL(href);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(2000);
			connection.setReadTimeout(2000);
			statusCode = connection.getResponseCode();
			connection.disconnect();
		} catch (Exception e) {
			System.out.println("Error checking the URL: " + e.getMessage());
		}
		return statusCode;
	}

}
