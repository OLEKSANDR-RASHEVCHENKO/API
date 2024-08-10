package HandlingDynamicJSON;

import files.Payload;
import files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;

import static io.restassured.RestAssured.*;

public class DynamicJson {
    @Test(dataProvider = "BooksData")
    public void addBook(String isbn,String aisle){
        RestAssured.baseURI = "http://216.10.245.166";
        String response=given().log().all().header("Content-Type","application/json")
                .body(Payload.addBook(isbn,aisle))
                .when()
                .post("Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();
        JsonPath jsonPath=ReUsableMethods.rowToJson(response);
        String id=jsonPath.getString("ID");
        System.out.println(id);
    }
    @DataProvider(name = "BooksData")
    public Object[][] getData(){
        // array = collection of elements
        //multidimensional array=collection of arrays
        return new Object[][]{{"odfod","9494"},{"sdfsdew","431"},{"dfdsfsd","40606"}};
    }
}
