package actions;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


@ExtendWith(RestAssuredExtension.class)
class ActionsIntegrationTest {

    private static Integer id;

    @BeforeAll
    static void beforeAll() {
        id = addAction();
    }

    @Test
    void getAction() {
        //@formatter:off
        when().
                get("/action/" + id).
        then().
                statusCode(200).
                assertThat().
                body("id", equalTo(id)).
                body("verb", equalTo("TAG")).
                body("objectType", equalTo("COMMENT")).
                body("objectUri", equalTo("https://yahoo.com"));
        //@formatter:on
    }

    @Test
    void createAction() {
        post("/action?verb=TAG&objectType=COMMENT&objectUri=https://yahoo.com")
                .then()
                .statusCode(200)
                .body("id", notNullValue());
    }

    private static Integer addAction() {
        return post("/action?verb=TAG&objectType=COMMENT&objectUri=https://yahoo.com")
                .then()
                .statusCode(200)
                .extract().path("id");
    }
}
