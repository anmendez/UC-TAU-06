package api_test;

import helpers.AuthHelper;
import helpers.DataHelper;
import model.Post;
import org.testng.annotations.Test;
import specifications.RequestSpecs;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class PostsTest extends BaseTest {
    String PostId = "/8137";
    String PostIdNull = "/8855";
    String resourcePath = "/v1/post";
    String resourcePathAll = "/v1/posts";

    @Test
    public void Test_Create_Post_Positive(){
        Post newPost = new Post(DataHelper.generateRandomTitle(), DataHelper.generateRandomContent());

        // Preparación
        given()
                .header("Authorization", String.format("Bearer %s", AuthHelper.getUserToken()))
                .body(newPost)
        // Ejecucion
        .when()
                .post(resourcePath)
        // Verificacion
        .then()
                .body("message", equalTo("Post created"))
                .statusCode(200);
    }
    @Test
    public void Test_Create_Post_Negative(){
        // Preparación Headers/Body
        given()
                .header("Authorization", String.format("Bearer %s", AuthHelper.getUserToken()))
                .body("{\"title\":\"Form with only one of the required values\"}")
        // Ejecucion
        .when()
                .post(resourcePath )
        // Assertions / Verificaciones
        .then()
                .body("message", equalTo("Invalid form"))
                .statusCode(406);
    }

    @Test
    public void Test_Get_All_Post_Positive(){

        // Preparación Headers/Body
        given()
                .header("Authorization", String.format("Bearer %s", AuthHelper.getUserToken()))
        // Ejecucion
        .when()
                .get(resourcePathAll)
        // Assertions / Verificaciones
        .then()
                .statusCode(200);
    }
    @Test
    public void Test_Get_All_Post_Negative(){

        // Preparación Headers/Body
        given() //giving basic auth instead of bearer to break it
                .spec(RequestSpecs.generateBasicAuth())
        // Ejecucion
        .when()
                .get(resourcePathAll)
        // Assertions / Verificaciones
        .then()
                .body("message", equalTo("Please login first"))
                .statusCode(401);
    }

    @Test
    public void Test_Get_Single_Post_Positive(){

        // Headers/Body
        given()
                .header("Authorization", String.format("Bearer %s", AuthHelper.getUserToken()))
        // Ejecucion
        .when()
                .get(resourcePath + PostId)
        // Assertions / Verificaciones
        .then()
                .body("data.id", equalTo(8137))
                .statusCode(200);
    }
    @Test
    public void Test_Get_Single_Post_Negative(){
        //  Headers/Body
        given()
                .header("Authorization", String.format("Bearer %s", AuthHelper.getUserToken()))
        // Ejecucion
        .when()
                .get(resourcePath + PostIdNull)
        // Assertions / Verificaciones
        .then()
                .body("Message", equalTo("Post not found"))
                .statusCode(404);
    }

    @Test
    public void Test_Update_Post_Positive(){
        Post updatedPost = new Post(DataHelper.generateRandomTitle(), DataHelper.generateRandomContent());

        // Preparación
        given()
                .header("Authorization", String.format("Bearer %s", AuthHelper.getUserToken()))
                .body(updatedPost)
        // Ejecucion
        .when()
                .put(resourcePath + PostId)
        // Verificacion
        .then()
                .body("message", equalTo("Post updated"))
                .statusCode(200);
    }
    @Test
    public void Test_Update_Post_Negative(){

        // Preparación Headers/Body
        given()
                .header("Authorization", String.format("Bearer %s", AuthHelper.getUserToken()))
                .body("{\"title\":\"Form with only one of the required values\"}")
        // Ejecucion
        .when()
                .post(resourcePath )
        // Assertions / Verificaciones
        .then()
                .body("message", equalTo("Invalid form"))
                .statusCode(406);
    }

    @Test
    public void Test_Delete_Post_Positive(){
        // Preparación
        given()
                .header("Authorization", String.format("Bearer %s", AuthHelper.getUserToken()))
        // Ejecucion
        .when()
                .delete(resourcePath + PostId)
        // Verificacion
        .then()
                .body("message", equalTo("Post deleted"))
                .statusCode(200);
    }
    @Test
    public void Test_Delete_Post_Negative(){

        // Preparación Headers/Body
        given()
                .header("Authorization", String.format("Bearer %s", AuthHelper.getUserToken()))
        // Ejecucion
        .when()
                .delete(resourcePath + PostIdNull)
        // Assertions / Verificaciones
        .then()
                .body("message", equalTo("Post could not be deleted"))
                .statusCode(406);
    }
}
