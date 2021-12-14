package api_test;
import helpers.AuthHelper;
import helpers.DataHelper;
import model.Comment;
import org.testng.annotations.Test;
import specifications.RequestSpecs;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.IsEqual.equalTo;

public class CommentsTests extends BaseTest {

    String PostId = "8086";
    String PostIdNull = "8855";
    String resourcePath = "/v1/comment/";
    String resourcePathAll = "/v1/comments/";
    String CommentId = "/4790";
    String CommentIdNull = "/123";

    @Test
    public void Test_Create_Comment_Positive(){

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
    @Test
    public void Test_Create_Comment_Negative(){

        Comment newComment = new Comment(DataHelper.generateRandomName(), DataHelper.generateRandomContent());

        // Preparación
        given()
                .spec(RequestSpecs.generateBasicAuth())
                .body(newComment)
        // Ejecucion
        .when()
                .post(  resourcePath + PostIdNull)
        // Assertions / Verificaciones
        .then()
                .body("message", equalTo("Comment could not be created"))
                .statusCode(406);
    }

    @Test
    public void Test_Get_All_Comment_Positive(){

        // Preparación Headers/Body
        given()
                .spec(RequestSpecs.generateBasicAuth())
        // Ejecucion
        .when()
                .get(resourcePathAll + PostId)
        // Assertions / Verificaciones
        .then()
                .body("results.meta.total", contains(7))
                .statusCode(200);
    }
    @Test
    public void Test_Get_All_Comment_Negative(){

        // Preparación Headers/Body
        given() //giving bearer auth instead of basic to break it
                .spec(RequestSpecs.generateBasicAuth())
        // Ejecucion
        .when()
                .get(resourcePathAll + PostIdNull )
        // Assertions / Verificaciones
        .then()
                .body("results.meta.total", contains(0))
                .statusCode(200);
    }

    @Test
    public void Test_Get_Single_Comment_Positive(){

        // Preparación Headers/Body
        given()
                .spec(RequestSpecs.generateBasicAuth())
        // Ejecucion
        .when()
                .get(resourcePath + PostId + CommentId)
        // Assertions / Verificaciones
        .then()
                .body("data.post_id", equalTo(PostId))
                .statusCode(200);
    }
    @Test
    public void Test_Get_Single_Comment_Negative(){

        // Preparación Headers/Body
        given() //giving bearer auth instead of basic to break it
                .spec(RequestSpecs.generateBasicAuth())
        // Ejecucion
        .when()
                .get(resourcePath + PostIdNull + CommentId)
        // Assertions / Verificaciones
        .then()
                .body("Message", equalTo("Comment not found"))
                .statusCode(404);
    }

    @Test
    public void Test_Update_Comment_Positive(){

        Comment updatedComment = new Comment(DataHelper.generateRandomName(), DataHelper.generateRandomContent());
        // Preparación Headers/Body
        given()
                .spec(RequestSpecs.generateBasicAuth())
                .body(updatedComment)
        // Ejecucion
        .when()
                .put(resourcePath + PostId + CommentId)
        // Assertions / Verificaciones
        .then()
                .body("message", equalTo("Comment updated"))
                .statusCode(200);
    }
    @Test
    public void Test_Update_Comment_Negative(){

        Comment updatedComment = new Comment(DataHelper.generateRandomName(), DataHelper.generateRandomContent());
        // Preparación Headers/Body
        given()
                .spec(RequestSpecs.generateBasicAuth())
                .body("{\"name\":\"Form with only one of the required values\"}")
        // Ejecucion
        .when()
                .put(resourcePath + PostId + CommentId)
        // Assertions / Verificaciones
        .then()
                .body("message", equalTo("Invalid form"))
                .statusCode(406);
    }

    @Test
    public void Test_Delete_Comment_Positive(){
        // Preparación
        given()
                .spec(RequestSpecs.generateBasicAuth())
                // Ejecucion
                .when()
                .delete(resourcePath + PostId + CommentId)
                // Verificacion
                .then()
                .body("message", equalTo("Comment deleted"))
                .statusCode(200);
    }
    @Test
    public void Test_Delete_Comment_Negative(){

        // Preparación Headers/Body
        given()
                .spec(RequestSpecs.generateBasicAuth())
                // Ejecucion
                .when()
                .delete(resourcePath + PostId + CommentIdNull)
                // Assertions / Verificaciones
                .then()
                .body("message", equalTo("Comment could not be deleted"))
                .statusCode(406);
    }
}
