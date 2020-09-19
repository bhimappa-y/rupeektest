package com.rupeek.testCases;

import com.rupeek.base.TestBase;
import io.restassured.http.Method;
import kong.unirest.json.JSONArray;

import kong.unirest.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.testng.Assert;
import org.testng.annotations.*;


import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class TC002_Get_All_Users extends TestBase {

    static RequestSpecification httpRequest;
    static Response response, response01;
    static String username = "rupeek";
    static String password = "password";
    private static JSONObject json;
	static String [] phoneNumbers;

    @BeforeClass
    public void getEmployeeData() throws InterruptedException {
        logger.info("********* Started TC002_Get_All_Users **********");
        RestAssured.baseURI = "http://13.126.80.194:8080";
        httpRequest = RestAssured.given();
        response = TC001_LOG_IN_User.getResponce();
        httpRequest.header("Authorization", "Bearer " + response.getBody().jsonPath().get("token"));
    }


    //Test Case - Status code validation
    @Test
    void checkStatusCode() {
        response01 = httpRequest.request(Method.GET, "/api/v1/users");
        logger.info(response01.getBody().print());

        //logger.info();
        int statusCode = response01.getStatusCode();
        Assert.assertEquals(statusCode, 200);
    }

    @Test
    void listTheUser() throws ParseException {
        response01 = httpRequest.request(Method.GET, "/api/v1/users");
        logger.info(response01.getBody().print());

        String jsonString = response01.body().asString();
        JSONObject jsonObject = null;

		JSONArray ja = new JSONArray(jsonString);
		phoneNumbers = new String[ja.length()];
		for(int i=0 ; i< ja.length() ; i++) {
			JSONObject json = ja.getJSONObject(i);
			phoneNumbers[i] = json.getString("phone");
			logger.info(json.getString("phone"));
		}
        int statusCode = response01.getStatusCode();
        Assert.assertEquals(statusCode, 200);

    }

    @Test
	void getUserUsingPhoneNumber(){
			int statusCode = response01.getStatusCode();
			Assert.assertEquals(statusCode, 200);
	}

    @AfterClass
    void tearDown() {
        logger.info("*********  Finished TC002_Get_All_Users  **********");
    }

}
