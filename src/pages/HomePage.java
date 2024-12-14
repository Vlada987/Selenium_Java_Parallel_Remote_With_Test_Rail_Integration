package pages;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class HomePage extends BaseClass {

	public HomePage(WebDriver driver) {
		super(driver);

	}

	public <T> void filterProducts(T index) {
		Select select = new Select(driver.findElement(By.xpath("//select[@class='product_sort_container']")));
		if (index instanceof Integer) {
			select.selectByIndex((Integer) index);
		} else if (index instanceof String) {
			select.selectByValue((String) index);
		} else {
			throw new IllegalArgumentException("Unsupported type for index: " + index.getClass().getSimpleName());
		}

	}

	public List<String> getItemsPrices() {
		return driver.findElements(By.xpath(Byes.itemPriceXp)).stream().map(el -> el.getText())
				.collect(Collectors.toList());
	}

	public void openRandomProduct() {

		List<WebElement> productElements = driver.findElements(By.xpath("//div[@class='inventory_item_name ']"));
		if (!productElements.isEmpty()) {
			WebElement randomProduct = productElements.stream()
					.skip(ThreadLocalRandom.current().nextInt(productElements.size())).findFirst()
					.orElseThrow(() -> new RuntimeException("No product found"));
			randomProduct.click();
		} else {
			System.out.println("No products found.");
		}
	}

	public void addProductToCart() {
		clickOn(By.xpath(Byes.addToCartBtnXp));

	}

	public CartPage openCardPage() {
		clickOn(By.xpath("//a[@class='shopping_cart_link']"));
		return new CartPage(driver);
	}

}
