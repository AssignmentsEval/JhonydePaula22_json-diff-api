package com.waes.test.jsondiffapi.controller;

import com.waes.test.model.DiffResultDTO;
import com.waes.test.model.DiffsDTO;
import com.waes.test.model.ErrorDTO;
import com.waes.test.model.SideEnum;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.waes.test.jsondiffapi.TestConstants.BASE_URL;
import static com.waes.test.jsondiffapi.TestConstants.DIFF_JSON_PATH;
import static com.waes.test.jsondiffapi.TestConstants.INVALID_JSON_1;
import static com.waes.test.jsondiffapi.TestConstants.INVALID_JSON_2;
import static com.waes.test.jsondiffapi.TestConstants.INVALID_JSON_3;
import static com.waes.test.jsondiffapi.TestConstants.PORT;
import static com.waes.test.jsondiffapi.TestConstants.SAVE_JSON_PATH;
import static com.waes.test.jsondiffapi.TestConstants.VALID_JSON_1;
import static com.waes.test.jsondiffapi.TestConstants.VALID_JSON_2;
import static com.waes.test.jsondiffapi.TestConstants.VALID_JSON_3;
import static com.waes.test.model.DiffResultEnum.DIFFERENT_LENGHT;
import static com.waes.test.model.DiffResultEnum.EQUAL;
import static com.waes.test.model.DiffResultEnum.NOT_EQUAL;
import static com.waes.test.model.SideEnum.LEFT;
import static com.waes.test.model.SideEnum.RIGHT;
import static io.restassured.RestAssured.with;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class JsonOperationsControllerTest {

    @BeforeEach
    void setup() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.port = PORT;
    }

    @Test
    @Order(1)
    void should_save_data_on_left_side_of_json() {
        final SideEnum side = LEFT;
        final String id = "1";
        final String path = format(SAVE_JSON_PATH, id, side.toString());
        with().body(VALID_JSON_1).contentType("application/json").request("POST", path)
                .then().statusCode(204);
    }

    @Test
    @Order(2)
    void should_save_data_on_right_side_of_json() {
        final SideEnum side = RIGHT;
        final String id = "1";
        final String path = format(SAVE_JSON_PATH, id, side.toString());
        with().body(VALID_JSON_1).contentType("application/json").request("POST", path)
                .then().statusCode(204);
    }

    @Test
    @Order(3)
    void should_verify_equality_and_get_EQUAL_as_response() {
        final String id = "1";
        final String path = format(DIFF_JSON_PATH, id);
        final DiffResultDTO expected = new DiffResultDTO().result(EQUAL);
        Response response = with().contentType("application/json").request("GET", path).thenReturn();
        assertEquals(expected, response.getBody().as(DiffResultDTO.class));
        assertEquals(200, response.statusCode());
    }

    @Test
    @Order(4)
    void should_add_a_new_json_on_the_left_and_verify_equality_and_get_NOT_EQUAL_as_response() {
        final String id = "1";
        final SideEnum side = RIGHT;
        final String path = format(SAVE_JSON_PATH, id, side.toString());

        with().body(VALID_JSON_2).contentType("application/json").request("POST", path)
                .then().statusCode(204);

        final String diffPath = format(DIFF_JSON_PATH, id);
        final DiffResultDTO expected = new DiffResultDTO().result(NOT_EQUAL)
                .addDiffsItem(new DiffsDTO().offset(5).length(1))
                .addDiffsItem(new DiffsDTO().offset(8).length(4));

        Response response = with().contentType("application/json").request("GET", diffPath).thenReturn();
        assertEquals(expected, response.getBody().as(DiffResultDTO.class));
        assertEquals(200, response.statusCode());
    }

    @Test
    @Order(5)
    void should_add_a_new_json_on_the_left_and_verify_equality_and_get_DIFFERENT_LENGHT_as_response() {
        final String id = "1";
        final SideEnum side = RIGHT;
        final String path = format(SAVE_JSON_PATH, id, side.toString());

        with().body(VALID_JSON_3).contentType("application/json").request("POST", path)
                .then().statusCode(204);

        final String diffPath = format(DIFF_JSON_PATH, id);
        final DiffResultDTO expected = new DiffResultDTO().result(DIFFERENT_LENGHT);
        Response response = with().contentType("application/json").request("GET", diffPath).thenReturn();
        assertEquals(expected, response.getBody().as(DiffResultDTO.class));
        assertEquals(200, response.statusCode());
    }

    @Test
    @Order(6)
    void should_fail_trying_to_save_unencoded_data_on_right_side_of_json() {
        final SideEnum side = RIGHT;
        final String id = "2";
        final String path = format(SAVE_JSON_PATH, id, side.toString());
        final ErrorDTO expected = new ErrorDTO().message("The data provided is not in a valid Base64 format");
        Response response = with().body(INVALID_JSON_1).contentType("application/json").request("POST", path).thenReturn();
        assertEquals(400, response.statusCode());
        assertEquals(expected, response.getBody().as(ErrorDTO.class));
    }

    @Test
    @Order(7)
    void should_fail_trying_to_save_encoded_non_json_data_on_right_side_of_json() {
        final SideEnum side = RIGHT;
        final String id = "2";
        final String path = format(SAVE_JSON_PATH, id, side.toString());
        final ErrorDTO expected = new ErrorDTO().message("The data provided is not in a valid Json format");
        Response response = with().body(INVALID_JSON_2).contentType("application/json").request("POST", path).thenReturn();
        assertEquals(400, response.statusCode());
        assertEquals(expected, response.getBody().as(ErrorDTO.class));
    }

    @Test
    @Order(8)
    void should_fail_trying_to_save_invalid_base64_encode_data_on_right_side_of_json() {
        final SideEnum side = RIGHT;
        final String id = "2";
        final String path = format(SAVE_JSON_PATH, id, side.toString());
        final ErrorDTO expected = new ErrorDTO().message("The data provided is not in a valid Base64 format");
        Response response = with().body(INVALID_JSON_3).contentType("application/json").request("POST", path).thenReturn();
        assertEquals(400, response.statusCode());
        assertEquals(expected, response.getBody().as(ErrorDTO.class));
    }

    @Test
    @Order(9)
    void should_fail_to_return_diff_when_there_is_no_record_with_provided_id_on_data_base() {
        final String id = "3";
        final String diffPath = format(DIFF_JSON_PATH, id);
        final ErrorDTO expected = new ErrorDTO().message("No Json record was found. Try again with a valid ID");
        Response response = with().contentType("application/json").request("GET", diffPath).thenReturn();
        assertEquals(400, response.statusCode());
        assertEquals(expected, response.getBody().as(ErrorDTO.class));
    }

    @Test
    @Order(10)
    void should_save_data_on_left_side_of_json_and_fail_when_trying_to_see_diff_without_adding_data_on_the_other_side() {
        final SideEnum side = LEFT;
        final String id = "4";
        final String path = format(SAVE_JSON_PATH, id, side.toString());
        with().body(VALID_JSON_1).contentType("application/json").request("POST", path)
                .then().statusCode(204);

        final String diffPath = format(DIFF_JSON_PATH, id);
        final ErrorDTO expected = new ErrorDTO().message("Right Json is not present. Please add the data and try again");
        Response response = with().contentType("application/json").request("GET", diffPath).thenReturn();
        assertEquals(400, response.statusCode());
        assertEquals(expected, response.getBody().as(ErrorDTO.class));
    }

    @Test
    @Order(11)
    void should_save_data_on_right_side_of_json_and_fail_when_trying_to_see_diff_without_adding_data_on_the_other_side() {
        final SideEnum side = RIGHT;
        final String id = "5";
        final String path = format(SAVE_JSON_PATH, id, side.toString());
        with().body(VALID_JSON_1).contentType("application/json").request("POST", path)
                .then().statusCode(204);

        final String diffPath = format(DIFF_JSON_PATH, id);
        final ErrorDTO expected = new ErrorDTO().message("Left Json is not present. Please add the data and try again");
        Response response = with().contentType("application/json").request("GET", diffPath).thenReturn();
        assertEquals(400, response.statusCode());
        assertEquals(expected, response.getBody().as(ErrorDTO.class));
    }

    @Test
    @Order(12)
    void should_not_execute_request_if_side_is_not_in_a_valid_format() {
        final String side = "RIGHT";
        final String id = "5";
        final String path = format(SAVE_JSON_PATH, id, side);
        final ErrorDTO expected = new ErrorDTO().message(format("The parameter %s is not recognized as a valid side. The accepted values are 'right' and 'left'", side));

        Response response = with().body(VALID_JSON_1).contentType("application/json").request("POST", path).thenReturn();

        assertEquals(400, response.statusCode());
        assertEquals(expected, response.getBody().as(ErrorDTO.class));
    }
}