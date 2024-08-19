package RequestAndResponseSpecBuilder;

import SetializationAndDeserialization.Serialization.AddPlace;
import SetializationAndDeserialization.Serialization.Locations;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class SerializationReqAndResBilder {
    public static void main(String[] args) {
        RestAssured.baseURI = "https://rahulshettyacademy.com/";
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

        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com/").addQueryParam("key", "qaclick123")
                .setContentType(ContentType.JSON).build();

        ResponseSpecification res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();


        RequestSpecification response=given().spec(req)
                .body(place);
                Response response1 = response.when().post("maps/api/place/add/json")
                .then().spec(res).extract().response();

        String responseString = response1.asString();
        System.out.println(responseString);
    }
}
