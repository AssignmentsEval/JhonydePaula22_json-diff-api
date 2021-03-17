package com.waes.test.jsondiffapi;

import lombok.experimental.UtilityClass;

@UtilityClass
public class TestConstants {

    public static final String BASE_URL = "http://localhost";
    public static final int PORT = 8080;
    public static final String SAVE_JSON_PATH = "/v1/diff/%s/%s";
    public static final String DIFF_JSON_PATH = "/v1/diff/%s";

    public static final byte[] VALID_JSON_1 = "eyJ0ZXN0IjpudWxsfQ==".getBytes(); // {"test":null}
    public static final byte[] VALID_JSON_2 = "eyJ0ZXNiIjp0cnVlfQ==".getBytes(); // {"tesb":true}
    public static final byte[] VALID_JSON_3 = "eyJ0ZXN0IjpmYWxzZSwgImFub3RoZXJQcm9wIjogMX0=".getBytes(); //{"test":false, "anotherProp": 1}

    public static final byte[] DECODED_VALID_JSON_1 = "{\"test\":null}".getBytes();
    public static final byte[] DECODED_VALID_JSON_2 = "{\"tesb\":true}".getBytes();
    public static final byte[] DECODED_VALID_JSON_3 = "{\"test\":false, \"anotherProp\": 1}".getBytes();

    public static final byte[] INVALID_JSON_1 = "{\"test\":true}".getBytes(); // non Base64 encoded data
    public static final byte[] INVALID_JSON_2 = "c2ltcGxlLXN0cmluZw==".getBytes(); // simple-string
    public static final byte[] INVALID_JSON_3 = "c2ltcGxlLXN0cml79a0sc!ˆ&8...!!!".getBytes(); // invalid Base64 encode
}
