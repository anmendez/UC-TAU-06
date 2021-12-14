package specifications;

import helpers.AuthHelper;
import io.restassured.authentication.BasicAuthScheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class RequestSpecs {
    //Bearer
    public static RequestSpecification generateToken(){

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        String token = AuthHelper.getUserToken();
        // Add Header
        requestSpecBuilder.addHeader("Authorization", "Bearer "+token);

        return requestSpecBuilder.build();
    }
    public static RequestSpecification generateInvalidToken(){

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        String token = AuthHelper.getUserInvalidToken();
        // Add Header
        requestSpecBuilder.addHeader("Authorization", "Bearer "+token);

        return requestSpecBuilder.build();
    }

    //Basic Auth
    public static RequestSpecification generateBasicAuth(){
        BasicAuthScheme basicAuth = new BasicAuthScheme();
        basicAuth.setUserName("testuser");
        basicAuth.setPassword("testpass");

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setAuth(basicAuth);
        return requestSpecBuilder.build();
    }

}