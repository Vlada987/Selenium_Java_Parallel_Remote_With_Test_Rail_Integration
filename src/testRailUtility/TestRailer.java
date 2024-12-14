package testRailUtility;

import java.io.File;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class TestRailer {

	public static Response postResult(String jsonPayload, String credentials, String url) {
		return RestAssured.given().header("Authorization", "Basic " + credentials).contentType(ContentType.JSON)
				.body(jsonPayload).when().post(url);
	}

	public static Response postAttachment(String credentials, String url, File file) {
		return RestAssured.given().header("Authorization", "Basic " + credentials).contentType(ContentType.MULTIPART)
				.multiPart("attachment", file).when().post(url);
	}

}
