package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ChekoutPage extends BaseClass {

	public ChekoutPage(WebDriver driver) {
		super(driver);

	}

	public PaymentPage fillCheckoutForm() {
		wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//input"), 4));
		List<WebElement> elements = driver.findElements(By.xpath("//input"));
		elements.get(0).sendKeys(faker.name().firstName());
		elements.get(1).sendKeys(faker.name().lastName());
		elements.get(2).sendKeys(faker.address().zipCode());
		elements.get(3).click();
		return new PaymentPage(driver);
	}

}
