
/*
 * Author: Hemant Choudhari
 * summary: Test Case 3 Reset Password
 * Date: 09/14/2019
 */

/******************************************************
Test Name: Reset password existing user
URI: http: https://api2.fox.com/v2.0/reset
Request Type: POST
Request Payload(Body):

{
	"email":"hemant10@fox.com",
	"password":"abcdef"
}

********* Validations **********
message  and detail Validation
Status Code : 200
Response Time Validation
Status Line : HTTP/1.1 200 OK
Content Type : application/json


*********************************************************/

package com.rupeek.testCases;

import com.rupeek.base.TestBase;
import io.restassured.http.Method;
import io.restassured.specification.RequestSpecification;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;


import io.restassured.RestAssured;
import io.restassured.response.Response;

public class TC003_Get_User_Detail_For_Phone extends TestBase {


	private Response responce;
	static String[] phoneNumbers;
	private Response response01;


	@BeforeClass
	void getEmployeeData() throws InterruptedException{

		logger.info("********* Started TC003_Get_User_Detail_For_Phone **********");
		RestAssured.baseURI = "http://13.126.80.194:8080";
		httpRequest = RestAssured.given();

		response = TC001_LOG_IN_User.getResponce();
		httpRequest.header("Authorization", "Bearer " + response.getBody().jsonPath().get("token"));
		phoneNumbers = getAllPhoneNumbers(httpRequest);

	}

	private String []  getAllPhoneNumbers(RequestSpecification httpRequest) {
		String[] Numbers;
		Response response01;
		response01 = httpRequest.request(Method.GET, "/api/v1/users");
		logger.info(response01.getBody().print());

		String jsonString = response01.body().asString();
		JSONObject jsonObject = null;

		JSONArray ja = new JSONArray(jsonString);
		Numbers = new String[ja.length()];
		for(int i=0 ; i< ja.length() ; i++) {
			JSONObject json = ja.getJSONObject(i);
			Numbers[i] = json.getString("phone");
			logger.info(json.getString("phone"));
		}

		return Numbers;
	}


	//Test Case - message  and detail Validation
	@Test
	void checkResposeBody()
	{

		for (int i=0 ; i< phoneNumbers.length ; i++) {
			response01 = httpRequest.request(Method.GET, "/api/v1/users/"+phoneNumbers[i]);
			int statusCode = response01.getStatusCode();
			logger.info(response01.getBody().print());
			Assert.assertEquals(statusCode, 200);
		}


	}

	//Test Case - Status code validation
	@Test
	void checkStatusCode()
	{
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 200);
	}

	//Test Case - Response Time check
	@Test
	void checkResponseTime()
	{
		long responseTime = response.getTime();
		Assert.assertTrue(responseTime<7000);

	}

	//Test Case - Check status Line
	@Test
	void checkstatusLine()
	{
		String statusLine = response.getStatusLine();
		Assert.assertEquals(statusLine, "HTTP/1.1 200 ");

	}


	@AfterClass
	void tearDown()
	{
		logger.info("*********  Finished TC003_Get_User_Detail_For_Phone  **********");
	}

}
