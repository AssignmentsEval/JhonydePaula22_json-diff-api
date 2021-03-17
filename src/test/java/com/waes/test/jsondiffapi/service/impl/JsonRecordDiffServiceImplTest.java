package com.waes.test.jsondiffapi.service.impl;

import com.waes.test.jsondiffapi.exception.JsonDataNotPresentException;
import com.waes.test.jsondiffapi.model.entity.JsonRecord;
import com.waes.test.jsondiffapi.repository.JsonRecordRepository;
import com.waes.test.model.DiffResultDTO;
import com.waes.test.model.DiffsDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.waes.test.jsondiffapi.TestConstants.DECODED_VALID_JSON_1;
import static com.waes.test.jsondiffapi.TestConstants.DECODED_VALID_JSON_2;
import static com.waes.test.jsondiffapi.TestConstants.DECODED_VALID_JSON_3;
import static com.waes.test.model.DiffResultEnum.DIFFERENT_LENGHT;
import static com.waes.test.model.DiffResultEnum.EQUAL;
import static com.waes.test.model.DiffResultEnum.NOT_EQUAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JsonRecordDiffServiceImplTest {

    @Mock
    private JsonRecordRepository jsonRecordRepository;

    @InjectMocks
    private JsonRecordDiffServiceImpl service;

    @Test
    void should_return_equals_when_json_record_has_json_on_both_sides_and_their_content_are_equal() {
        final String id = "1";
        final JsonRecord persistedJsonRecord = JsonRecord.builder()
                .id(id)
                .right_json(DECODED_VALID_JSON_1)
                .left_json(DECODED_VALID_JSON_1)
                .build();
        final DiffResultDTO expected = new DiffResultDTO().result(EQUAL);

        when(jsonRecordRepository.findById(id)).thenReturn(Optional.of(persistedJsonRecord));

        assertEquals(expected, service.getJsonDiffs(id));

        verify(jsonRecordRepository).findById(id);
    }

    @Test
    void should_throw_JsonDataNotPresentException_when_json_record_has_json_only_on_left_side() {
        final String id = "1";
        final JsonRecord persistedJsonRecord = JsonRecord.builder()
                .id(id)
                .left_json(DECODED_VALID_JSON_1)
                .build();

        when(jsonRecordRepository.findById(id)).thenReturn(Optional.of(persistedJsonRecord));

        assertThrows(JsonDataNotPresentException.class, () -> service.getJsonDiffs(id));

        verify(jsonRecordRepository).findById(id);
    }

    @Test
    void should_throw_JsonDataNotPresentException_when_json_record_has_json_only_on_right_side() {
        final String id = "1";
        final JsonRecord persistedJsonRecord = JsonRecord.builder()
                .id(id)
                .right_json(DECODED_VALID_JSON_1)
                .build();

        when(jsonRecordRepository.findById(id)).thenReturn(Optional.of(persistedJsonRecord));

        assertThrows(JsonDataNotPresentException.class, () -> service.getJsonDiffs(id));

        verify(jsonRecordRepository).findById(id);
    }

    @Test
    void should_return_not_equals_when_json_record_has_json_on_both_sides_and_their_content_are_equal_in_length_but_different_in_content() {
        final String id = "1";
        final JsonRecord persistedJsonRecord = JsonRecord.builder()
                .id(id)
                .right_json(DECODED_VALID_JSON_1)
                .left_json(DECODED_VALID_JSON_2)
                .build();
        final DiffResultDTO expected = new DiffResultDTO().result(NOT_EQUAL)
                .addDiffsItem(new DiffsDTO().offset(5).length(1))
                .addDiffsItem(new DiffsDTO().offset(8).length(4));

        when(jsonRecordRepository.findById(id)).thenReturn(Optional.of(persistedJsonRecord));

        assertEquals(expected, service.getJsonDiffs(id));

        verify(jsonRecordRepository).findById(id);
    }

    @Test
    void should_return_different_length_when_json_record_has_json_on_both_sides_and_their_content_are_not_equal_in_length() {
        final String id = "1";
        final JsonRecord persistedJsonRecord = JsonRecord.builder()
                .id(id)
                .right_json(DECODED_VALID_JSON_1)
                .left_json(DECODED_VALID_JSON_3)
                .build();
        final DiffResultDTO expected = new DiffResultDTO().result(DIFFERENT_LENGHT);

        when(jsonRecordRepository.findById(id)).thenReturn(Optional.of(persistedJsonRecord));

        assertEquals(expected, service.getJsonDiffs(id));

        verify(jsonRecordRepository).findById(id);
    }
}