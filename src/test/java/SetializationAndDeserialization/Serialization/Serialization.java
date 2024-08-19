package SetializationAndDeserialization.Serialization;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;

public class Serialization {
    public static void main(String[] args) {
        AddPlace place = new AddPlace();
        place.setAccuracy(50);
        place.setAddress("Dresdner str 8");
        place.setLanguage("Deutsch");
        place.setPhone_number("59499302304");
        place.setWebsite("https://dsfsdfsdfsdf");
        place.setName("Alex");
        List<String > myLIst = new ArrayList<String>();
        myLIst.add("shoe park");
        myLIst.add("shop");
        place.setTypes(myLIst);
        Locations locations = new Locations();
        locations.setLat(-45.54554);
        locations.setLng(-12.3333);
        place.setLocation(locations);

        RestAssured.baseURI = "https://rahulshettyacademy.com/";
        Response response=given().log().all().queryParam("key","qaclick123")
                .body(place)
                .when().post("maps/api/place/add/json")
                .then().assertThat().statusCode(200).extract().response();

        String responseString = response.asString();
        System.out.println(responseString);
    }
}
