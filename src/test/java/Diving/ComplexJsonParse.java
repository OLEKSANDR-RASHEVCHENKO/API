package Diving;

import files.Payload;
import io.restassured.path.json.JsonPath;
import jdk.security.jarsigner.JarSigner;

public class ComplexJsonParse {

    // Json in Payload coursePrice
    public static void main(String[] args) {
        JsonPath jsonPath = new JsonPath(Payload.coursePrice());
        // Print No of courses returned by API
        int count = jsonPath.getInt("courses.size()"); // use methode size only for Array
        System.out.println(count);
        //Print Purchase Amount
        int totalAmount=jsonPath.getInt("dashboard.purchaseAmount");
        System.out.println(totalAmount);
        //Print Title of the first course
        String titleFirstCourse=jsonPath.getString("courses[0].title");
        System.out.println(titleFirstCourse);

    }
}
