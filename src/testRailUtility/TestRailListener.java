package testRailUtility;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import io.restassured.response.Response;
import tests.BaseTest;
import util.MyUtil;

public class TestRailListener implements ITestListener {

	private static final String TESTRAIL_API_URL = "https://dovla***.testrail.io/";
	private static final String TESTRAIL_USERNAME = "***";
	private static final String TESTRAIL_API_KEY = "***";
	private static final long TEST_RUN_ID = 2;
	private static WebDriver driver;
	String comment = "";
	String currentBrowser="";

	
	
	@Override
	public void onTestStart(ITestResult result) {
		try {
			currentBrowser = MyUtil.getBrowserName(result);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	@Override
	public void onTestSuccess(ITestResult result) {

		try {
			sendTestResultToTestRail(result, "1", currentBrowser);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onTestFailure(ITestResult result) {
		try {
			driver = MyUtil.getDriverInstance(result);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		try {
			sendTestResultToTestRail(result, "5", currentBrowser);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {

		try {
			sendTestResultToTestRail(result, "2", currentBrowser);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void sendTestResultToTestRail(ITestResult result, String statusId, String currentBrowser)
			throws IOException {

		String testCaseId = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(ListenerCase.class)
				.value();

		if (result.getStatus() == ITestResult.FAILURE) {
			comment = "Test Failed :( " + result.getThrowable() + " " + result.getName() + " on browser:"
					+ currentBrowser;
		} else if (result.getStatus() == ITestResult.SKIP) {
			comment = "Test Skiiped " + result.getName();
		} else if (result.getStatus() == ITestResult.SUCCESS) {
			comment = "Test passed :) " + result.getName() + " on browser:" + currentBrowser;
		}

		String resultUrl = TESTRAIL_API_URL + "index.php?/api/v2/add_result_for_case/" + TEST_RUN_ID + "/" + testCaseId;
		String jsonPayload = "{" + "\"status_id\": \"" + statusId + "\"," + "\"comment\": \"" + comment + "\"" + "}";

		String credentials = MyUtil.encodeToBase64(TESTRAIL_USERNAME, TESTRAIL_API_KEY);

		Response response = TestRailer.postResult(jsonPayload, credentials, resultUrl);
		String rstatusId = response.jsonPath().get("status_id").toString();
		String resultId = response.jsonPath().get("id").toString();
		if (rstatusId.equals("5")) {
			String attachmentUrl = TESTRAIL_API_URL + "index.php?/api/v2/add_attachment_to_result/" + resultId;
			File file = PrntScr.takeScreenshot(driver);
			TestRailer.postAttachment(credentials, attachmentUrl, file);
		}

	}



}
