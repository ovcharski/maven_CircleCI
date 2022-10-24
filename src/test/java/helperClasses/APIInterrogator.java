package helperClasses;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;


/**
 * Wiki RestAssured - https://github.com/rest-assured/rest-assured/wiki/Usage
 */
public class APIInterrogator {

    public APIInterrogator() {

    }

    public static void test_APIWithOAuth2Authentication_ShouldBeGivenAccess() {
        String token = "";
        String url = "";

        given().
                auth().
                oauth2(token).
                when().
                get(url).
                then().
                assertThat().
                statusCode(200);
    }

    public static void test_APIWithBasicAuthentication_ShouldBeGivenAccess() {
        String username = "";
        String password = "";
        String url = "";

        given().
                auth().
                preemptive().
                basic(username, password).
                when().
                get(url).
                then().
                assertThat().
                statusCode(200);
    }
}
