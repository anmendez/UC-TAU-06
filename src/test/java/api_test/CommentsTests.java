package api_test;

import helpers.DataHelper;
import model.Comment;
import org.testng.annotations.Test;
import specifications.RequestSpecs;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class CommentsTests extends BaseTest {

    String PostId = "8094";
    String resourcePath = "/v1/comment/";

    @Test
    public void Test_Create_Comment(){

        Comment newComment = new Comment(DataHelper.generateRandomName(), DataHelper.generateRandomContent());

        // Preparación Headers/Body
        given()
                .spec(RequestSpecs.generateBasicAuth())
                .body(newComment)
        // Ejecucion
        .when()
                .post(  resourcePath + PostId)
        // Assertions / Verificaciones
        .then()
                .body("message", equalTo("Comment created"))
                .statusCode(200);
    }
}
