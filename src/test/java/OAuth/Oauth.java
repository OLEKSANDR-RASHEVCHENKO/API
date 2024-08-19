package OAuth;

import SetializationAndDeserialization.Deserialization.Api;
import SetializationAndDeserialization.Deserialization.GetCourse;
import SetializationAndDeserialization.Deserialization.WebAutomation;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;

public class Oauth {
    public static void main(String[] args) {
        String [] courseTitles = {"Selenium Webdriver Java","Cypress","Protractor"};
        String response=given().formParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
                .formParams("grant_type","client_credentials")
                .formParams("scope","trust")
                .when().log().all()
                .post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();
        System.out.println(response);
        JsonPath jsonPath = new JsonPath(response);
        String access=jsonPath.getString("access_token");
        System.out.println(access);

        GetCourse getCourse=given().queryParam("access_token",access)
                .when().log().all().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(GetCourse.class);
        System.out.println(getCourse.getLinkedIn());
        System.out.println(getCourse.getInstructor());
        System.out.println(getCourse.getCourses().getApi().get(1).getCourseTitle());
        List<Api> apiCourses=getCourse.getCourses().getApi();
        for (int i = 0;i<apiCourses.size();i++){
            if (apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")){
                System.out.println(apiCourses.get(i).getPrice());
            }

            //get te course names of WebAutomation
            ArrayList <String> actual = new ArrayList<String>();
            List<WebAutomation> webAutomation =getCourse.getCourses().getWebAutomation();
            for (int j=0;j<webAutomation.size();j++){
                actual.add(webAutomation.get(j).getCourseTitle());
            }
            List<String> expected = Arrays.asList(courseTitles);

            Assert.assertTrue(actual.equals(expected));
        }
    }
}
