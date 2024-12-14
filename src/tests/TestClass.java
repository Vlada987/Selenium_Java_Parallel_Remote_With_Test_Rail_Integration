package tests;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import org.testng.Assert;

import pages.Byes;
import pages.CartPage;
import pages.ChekoutPage;
import pages.HomePage;
import pages.PaymentPage;
import testRailUtility.ListenerCase;
import testRailUtility.TestRailListener;
import util.MyUtil;

@Listeners(TestRailListener.class)
public class TestClass extends BaseTest {

	HomePage homePage;
	CartPage cartPage;
	ChekoutPage checkoutpg;
	PaymentPage paymentPage;

	String currentProductName = "";

	@ListenerCase("1")
	@Test(priority = 0)
	public void test_verify_page_title() {
		homePage = new HomePage(getDriver());
		homePage.navigate(Byes.sauceDemoUrl);
		String currentTitle = homePage.getFromPage("title");

		Assert.assertEquals(currentTitle, "Swag Labs");
	}

	@ListenerCase("2")
	@Test(priority = 1)
	public void test_verify_login() {
		homePage.enterText(By.xpath(Byes.saucedUnameXp), Byes.saucedUname);
		homePage.enterText(By.xpath(Byes.saucedPwdXp), Byes.saucedPwd);
		homePage.clickOn(By.xpath(Byes.saucedLbtnXp));

		String url = homePage.getFromPage("url");

		Assert.assertTrue(url.contains("inventory"));
	}

	@ListenerCase("3")
	@Test(priority = 2)
	public void test_verify_price_filter() {
		homePage.filterProducts(2);
		List<Double> prices = MyUtil.pricesToDouble(homePage.getItemsPrices());
		Boolean sorted = MyUtil.isSorted(prices);

		Assert.assertTrue(sorted);

	}

	@ListenerCase("4")
	@Test(priority = 3)
	public void test_inspect_product_and_veriry_add_to_cart_btn() {
		homePage.openRandomProduct();
		currentProductName = homePage.getText(By.xpath(Byes.productHeader));
		Boolean addToCartFlag = homePage.isElementPresent(By.xpath(Byes.addToCartBtnXp));

		Assert.assertTrue(addToCartFlag);

	}

	@ListenerCase("5")
	@Test(priority = 4)
	public void test_add_to_cart() {
		homePage.addProductToCart();
		String textAfterAdd = homePage.getText(By.xpath(Byes.removeBtnXp));
		String cardBadgeNotif = homePage.getText(By.xpath(Byes.cardBadgeXp));

		Assert.assertEquals(textAfterAdd, "Remove");
		Assert.assertEquals(cardBadgeNotif, "1");

	}

	@ListenerCase("6")
	@Test(priority = 5)
	public void test_cart_page() {
		cartPage = homePage.openCardPage();
		String addedItem = cartPage.getText(By.xpath(Byes.cartPageItemXp));

		Assert.assertEquals(currentProductName, addedItem);
	}

	@ListenerCase("7")
	@Test(priority = 6)
	public void test_entering_chekoutPage() {

		checkoutpg = cartPage.openCheckoutpg();
		String pageTitle = checkoutpg.getText(By.xpath(Byes.titleXp));

		Assert.assertEquals(pageTitle, "Checkout: Your Information");
	}

	@ListenerCase("8")
	@Test(priority = 7)
	public void test_fill_and_submit_checkout_form() {

		paymentPage = checkoutpg.fillCheckoutForm();
		String pageTitle = paymentPage.getText(By.xpath(Byes.titleXp));

		Assert.assertEquals(pageTitle, "Checkout: Overview");

	}

	@ListenerCase("9")
	@Test(priority = 8)
	public void test_orderedProduct_and_other_data_during_payment() {

		String itemName = paymentPage.getText(By.xpath(Byes.itemXp));
		String pageTitle = paymentPage.getText(By.xpath(Byes.titleXp));
		String totalPrice = paymentPage.getText(By.xpath(Byes.totalPriceXp));

		Assert.assertEquals(itemName, currentProductName);
		Assert.assertTrue(totalPrice.contains("$"));
	}

	@ListenerCase("10")
	@Test(priority = 9)
	public void test_finish_payment() {

		paymentPage.executePayment();
		String headermsg = paymentPage.getText(By.xpath(Byes.completeHeaderXp));

		Assert.assertEquals(headermsg, "Thank you for your order!");

	}

	@ListenerCase("11")
	@Test(priority = 10)
	public void test_logout_and_verify_logoutPage_presence() {

		paymentPage.logOut();
		Boolean loginpFlag = homePage.isElementPresent(By.xpath(Byes.saucedUnameXp));

		Assert.assertTrue(loginpFlag);

	}

}
