package Diving;

import files.Payload;
import io.restassured.path.json.JsonPath;
import jdk.security.jarsigner.JarSigner;
import org.testng.Assert;
import org.testng.annotations.Test;

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
        //Print All course titles and their respective Price
        for (int i=0;i<count;i++){
            String title = jsonPath.getString("courses["+i+"].title");
            int price=jsonPath.getInt("courses["+i+"].price");
            System.out.println(title+" "+price+"$");
        }
        //Print only RPA course
        for (int i=0;i<count;i++){
            String title = jsonPath.getString("courses["+i+"].title");
            if (title.equalsIgnoreCase("RPA")){
                int copiesOfRPA=jsonPath.get("courses["+i+"].copies");
                System.out.println(copiesOfRPA);
                break;
            }
        }
    }
    //Verify is Sum of All course prices matches with Purchase Amount
    @Test
    public void sumValidation(){
        int sum  = 0;
        JsonPath jsonPath = new JsonPath(Payload.coursePrice());
        int count = jsonPath.getInt("courses.size()");
        for (int i=0;i<count;i++){
            int price=jsonPath.get("courses["+i+"].price");
            int copies=jsonPath.get("courses["+i+"].copies");
            int amount = price*copies;
            System.out.println(amount);
            sum = sum+amount;
        }
        int totalSumOfCourses=jsonPath.getInt("dashboard.purchaseAmount");
        Assert.assertEquals(sum,totalSumOfCourses);

    }
}
