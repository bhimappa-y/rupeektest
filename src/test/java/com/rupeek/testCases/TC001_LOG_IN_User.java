/*
 * Author: Hemant Choudhari
 * summary: Test Case 1 Register User
 * Date: 09/14/2019
 */

/******************************************************
URI:   http://13.126.80.194:8080
Request Type: POST
Request Payload(Body):
{
 "username":"rupeek",
 "password":"password"
 }

********* Validations **********
Status Code : 200
Status Line : HTTP/1.1 200 OK
Content Type : application/json

**********************************************************/

package com.rupeek.testCases;

import com.rupeek.base.TestBase;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TC001_LOG_IN_User extends TestBase {

	static RequestSpecification httpRequest;
	static Response response;

	@BeforeClass
	void createEmployee() throws InterruptedException
	{
		logger.info("********* Started TC001_LOG_IN_user  **********");
		getResponce();
		Thread.sleep(5000);

	}

	public static Response getResponce() {
		RestAssured.baseURI = "http://13.126.80.194:8080";
		httpRequest = RestAssured.given();

		// JSONObject is a class that represents a simple JSON. We can add Key-Value pairs using the put method

		JSONObject requestParams = new JSONObject();
		requestParams.put("username", "rupeek");
		requestParams.put("password", "password");

		// Add a header stating the Request body is a JSON
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("Cache-Control", "no-cache");

		// Add the Json to the body of the request
		httpRequest.body(requestParams.toJSONString());
		response = httpRequest.request(Method.POST, "/authenticate");
		return response;
	}


	@Test
	void checkstatusLine()
	{
		String statusLine = response.getStatusLine(); // get the  status Line
		Assert.assertEquals(statusLine, "HTTP/1.1 200 ");
		
	}

	@AfterClass
	void tearDown(){
		logger.info("*********  Finished C001_LOG_IN_user **********");
	}

}
