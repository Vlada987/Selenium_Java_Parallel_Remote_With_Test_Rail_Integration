package util;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import tests.BaseTest;
import tests.TestClass;

public class MyUtil {
 
	public static String getBrowserName(ITestResult result) throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		Object testClassInstance = result.getInstance();
		if (testClassInstance instanceof TestClass) {
			TestClass testClass = (TestClass) testClassInstance;
			WebDriver driver = testClass.getDriver();
			return driver.getClass().getSimpleName();

		} else {
			return "no browser found";
		}
	};

	public static WebDriver getDriverInstance(ITestResult result) throws InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		Object testClassInstance = result.getInstance();
		if (testClassInstance instanceof TestClass) {
			TestClass testClass = (TestClass) testClassInstance;
			return testClass.getDriver();
		}
		return null;
	};

	public static String getBrowserName(Class clazz) {
		String currentBrowser = "";
		try {
			Method setUpMethod = clazz.getDeclaredMethod("setUp", String.class);
			Parameter[] params = setUpMethod.getParameters();
			for (Parameter param : params) {
				if (param.getName().equals("browser")) {
					currentBrowser = param.getName();
				}
			}
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return currentBrowser;
	}

	public static List<Double> pricesToDouble(List<String> list) {
		return list.stream().map(el -> el.replace("$", "")).map(t -> Double.valueOf(t)).collect(Collectors.toList());
	};

	public static boolean isSorted(List<Double> list) {
		if (list == null || list.size() <= 1) {
			return true;
		}
		return IntStream.range(0, list.size() - 1).allMatch(i -> list.get(i) <= list.get(i + 1));
	}

	public static String encodeToBase64(String input) {

		byte[] encodedBytes = Base64.getEncoder().encode(input.getBytes());
		return new String(encodedBytes);
	}

	public static String encodeToBase64(String username, String apiKey) {

		String credentials = username + ":" + apiKey;
		String encodedCredentials = Base64.getEncoder().encodeToString(credentials.getBytes(StandardCharsets.US_ASCII));
		return encodedCredentials;
	}

	public static String encodeFileToBase64(String filePath) throws IOException {

		byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
		return Base64.getEncoder().encodeToString(fileBytes);
	}

}
