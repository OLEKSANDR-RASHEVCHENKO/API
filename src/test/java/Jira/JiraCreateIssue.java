package Jira;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import java.io.File;

import static io.restassured.RestAssured.*;

public class JiraCreateIssue {
    public static void main(String[] args) {
        RestAssured.baseURI="https://chattychatty.atlassian.net/";
        String createIssueResponse=given().header("Content-Type","application/json")
                .header("Authorization","Basic cmFzaGV2Y2hlbmtvb0BnbWFpbC5jb206QVRBVFQzeEZmR0YwQnVIeDYyZzQ2YVJrMWRfYWNMS2JrYzl1dG15SlFmdHRzWTMybXBYUEQweWRsTGNfWXotZEU1Sno4N1Y1QWtldWtCVXA3aW14WkJLaVVSSFB5ODRYYjRRWGNtR3B5czdXVGhoeGh2Wks2Rmx2MXRUSjlpemdqMVF4YTZkS3k1Q3o2ZXFsNEhsbGpNLXh6ZjRCUHFOZE5OZDhLd1JaVGxWRDNFSXYyckF5TkZFPTBDMzNEQjMy")
                .body("{\n" +
                        "    \"fields\": {\n" +
                        "       \"project\":\n" +
                        "       {\n" +
                        "          \"key\": \"AL\"\n" +
                        "       },\n" +
                        "       \"summary\": \"Linksssss is not worting\",\n" +
                        "       \"issuetype\": {\n" +
                        "          \"name\": \"Bug\"\n" +
                        "       }\n" +
                        "   }\n" +
                        "}")
                .log().all()
                .post("/rest/api/3/issue").then().log().all().assertThat().statusCode(201)
                .extract().response().asString();

        JsonPath jsonPath = new JsonPath(createIssueResponse);
        String issueId=jsonPath.getString("id");
        System.out.println(issueId);

        //Add Attachment
        given().pathParam("key",issueId)
                .header("X-Atlassian-Token","no-check")
                .header("Authorization","Basic cmFzaGV2Y2hlbmtvb0BnbWFpbC5jb206QVRBVFQzeEZmR0YwQnVIeDYyZzQ2YVJrMWRfYWNMS2JrYzl1dG15SlFmdHRzWTMybXBYUEQweWRsTGNfWXotZEU1Sno4N1Y1QWtldWtCVXA3aW14WkJLaVVSSFB5ODRYYjRRWGNtR3B5czdXVGhoeGh2Wks2Rmx2MXRUSjlpemdqMVF4YTZkS3k1Q3o2ZXFsNEhsbGpNLXh6ZjRCUHFOZE5OZDhLd1JaVGxWRDNFSXYyckF5TkZFPTBDMzNEQjMy")
                .multiPart("file",new File("C:/Users/Oleksandr/OneDrive/Рабочий стол/1.pdf"))
                .log().all().post("rest/api/3/issue/{key}/attachments")
                .then().log().all().assertThat().statusCode(200);
    }
}
