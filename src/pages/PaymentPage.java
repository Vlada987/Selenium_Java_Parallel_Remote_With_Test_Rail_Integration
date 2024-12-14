package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class PaymentPage extends BaseClass {

	public PaymentPage(WebDriver driver) {
		super(driver);
	}

	public void executePayment() {

		clickOn(By.xpath("//button[@id='finish']"));

	}

	public void logOut() {

		clickOn(By.xpath("//button[@id='react-burger-menu-btn']"));
		clickOn(By.xpath("//a[@id='logout_sidebar_link']"));
	}

}
