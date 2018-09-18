package actions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


@ExtendWith(RestAssuredExtension.class)
class ActionsIntegrationTest {

    @DisplayName("Retrieve an action by id")
    @Test
    void getAction() {
        int id = addAction();

        //@formatter:off
        given().
                pathParam("id", id).
        when().
                get("/action/{id}").
        then().
                statusCode(200).
                assertThat().
                body("id", equalTo(id)).
                body("verb", equalTo("TAG")).
                body("objectType", equalTo("COMMENT")).
                body("objectUri", equalTo("https://yahoo.com"));
        //@formatter:on
    }

    @DisplayName("Create an action")
    @Test
    void createAction() {
        //@formatter:off
        when().
                post("/action?verb=TAG&objectType=COMMENT&objectUri=https://yahoo.com").
        then().
                statusCode(200).
                body("id", notNullValue()).
                body("verb", equalTo("TAG")).
                body("objectType", equalTo("COMMENT")).
                body("objectUri", equalTo("https://yahoo.com"));
        //@formatter:on
    }

    @DisplayName("Delete an action by id")
    @Test
    void deleteAction() {
        int id = addAction();

        //@formatter:off
        given().
                pathParam("id", id).
        when().
                delete("/action/{id}").
        then()
                .statusCode(200);
        //@formatter:on
    }

    private  Integer addAction() {
        return post("/action?verb=TAG&objectType=COMMENT&objectUri=https://yahoo.com")
                .then()
                .statusCode(200)
                .extract().path("id");
    }
}
