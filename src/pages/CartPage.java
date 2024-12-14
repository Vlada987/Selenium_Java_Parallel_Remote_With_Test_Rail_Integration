package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BaseClass {

	public CartPage(WebDriver driver) {
		super(driver);

	}

	public ChekoutPage openCheckoutpg() {
		clickOn(By.xpath(Byes.chekoutBtnXp));
		return new ChekoutPage(driver);
	}

}
