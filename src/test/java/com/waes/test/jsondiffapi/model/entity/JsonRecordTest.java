package com.waes.test.jsondiffapi.model.entity;

import org.junit.jupiter.api.Test;

import static com.waes.test.jsondiffapi.TestConstants.VALID_JSON_1;
import static com.waes.test.jsondiffapi.TestConstants.VALID_JSON_3;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JsonRecordTest {

    @Test
    void should_perform_isLeftSideNull_tests() {
        final JsonRecord jsonRecord = JsonRecord.builder().build();

        assertTrue(jsonRecord.isLeftSideNull());

        jsonRecord.setLeft_json(VALID_JSON_1);
        assertFalse(jsonRecord.isLeftSideNull());
    }

    @Test
    void should_perform_isRightSideNull_tests() {
        final JsonRecord jsonRecord = JsonRecord.builder().build();

        assertTrue(jsonRecord.isRightSideNull());

        jsonRecord.setRight_json(VALID_JSON_1);
        assertFalse(jsonRecord.isRightSideNull());
    }

    @Test
    void should_perform_bothJsonsAreNotNull_tests() {
        final JsonRecord jsonRecord = JsonRecord.builder().build();

        assertFalse(jsonRecord.bothJsonsAreNotNull());

        jsonRecord.setLeft_json(VALID_JSON_1);
        assertFalse(jsonRecord.bothJsonsAreNotNull());

        jsonRecord.setRight_json(VALID_JSON_1);
        assertTrue(jsonRecord.bothJsonsAreNotNull());

        jsonRecord.setLeft_json(null);
        assertFalse(jsonRecord.bothJsonsAreNotNull());
    }

    @Test
    void should_perform_jsonsAreDifferentInLength_tests() {
        final JsonRecord jsonRecord = JsonRecord.builder().build();

        assertFalse(jsonRecord.jsonsAreDifferentInLength());

        jsonRecord.setLeft_json(VALID_JSON_1);
        assertFalse(jsonRecord.jsonsAreDifferentInLength());

        jsonRecord.setRight_json(VALID_JSON_1);
        assertFalse(jsonRecord.jsonsAreDifferentInLength());

        jsonRecord.setRight_json(VALID_JSON_3);
        assertTrue(jsonRecord.jsonsAreDifferentInLength());

        jsonRecord.setLeft_json(null);
        assertFalse(jsonRecord.jsonsAreDifferentInLength());
    }
}