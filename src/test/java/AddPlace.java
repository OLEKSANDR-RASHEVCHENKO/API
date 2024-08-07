import files.Payload;
import files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

public class AddPlace {
    public static void main(String[] args) {
        //validate is Add Place Api is working as expected
        //given -- all input details
        //when -- Submit the Api -resource,http method
        //Then -- validate the response
        //Add place-->Update Place with new Address -> GetPlace to validate if New address is present in response
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response=given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body(Payload.AddPlace()).when().post("/maps/api/place/add/json")
                .then().assertThat().statusCode(200).body("scope",equalTo("APP"))
                .header("server","Apache/2.4.52 (Ubuntu)").extract().response().asString();
        System.out.println(response);
        JsonPath jsonPath = new JsonPath(response);// for parsing JSON

        String placeID = jsonPath.getString("place_id");
        System.out.println("UPDATE PLACE");
        String newAddress = "70 Summer walk, USA";

        // update place
        given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body("{\n" +
                        "\"place_id\":\""+placeID+"\",\n" +
                        "\"address\":\""+newAddress+"\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}")
                .when().put("/maps/api/place/update/json")
                .then().assertThat().statusCode(200).body("msg",equalTo("Address successfully updated"));

        //Get Place

        String getPlaceResponse=given().log().all().queryParam("key","qaclick123")
                .queryParam("place_id",placeID)
                .when().get("/maps/api/place/get/json")
                .then().assertThat().log().all().statusCode(200).extract().response().asString();

        JsonPath jsonPath1=ReUsableMethods.rowToJson(getPlaceResponse);
        String actualAddress=jsonPath1.getString("address");
        System.out.println(actualAddress);
        Assert.assertEquals(newAddress,actualAddress);


    }
}
