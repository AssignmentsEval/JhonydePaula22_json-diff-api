package com.waes.test.jsondiffapi.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waes.test.jsondiffapi.exception.InvalidDataException;
import com.waes.test.jsondiffapi.model.entity.JsonRecord;
import com.waes.test.jsondiffapi.repository.JsonRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.waes.test.jsondiffapi.TestConstants.DECODED_VALID_JSON_1;
import static com.waes.test.jsondiffapi.TestConstants.INVALID_JSON_1;
import static com.waes.test.jsondiffapi.TestConstants.INVALID_JSON_2;
import static com.waes.test.jsondiffapi.TestConstants.INVALID_JSON_3;
import static com.waes.test.jsondiffapi.TestConstants.VALID_JSON_1;
import static com.waes.test.model.SideEnum.LEFT;
import static com.waes.test.model.SideEnum.RIGHT;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class JsonRecordServiceImplTest {

    @Mock
    private JsonRecordRepository jsonRecordRepository;

    private JsonRecordServiceImpl service;

    @BeforeEach
    void setup() {
        service = new JsonRecordServiceImpl(new ObjectMapper(), jsonRecordRepository);
    }

    @Test
    void should_save_letf_json_on_a_new_record() {
        final String id = "1";
        final JsonRecord jsonRecord = JsonRecord.builder()
                .id(id)
                .left_json(DECODED_VALID_JSON_1)
                .build();

        when(jsonRecordRepository.findById(id)).thenReturn(Optional.empty());
        when(jsonRecordRepository.save(jsonRecord)).thenReturn(jsonRecord);

        service.save(id, LEFT, VALID_JSON_1);

        verify(jsonRecordRepository).findById(id);
        verify(jsonRecordRepository).save(jsonRecord);
    }

    @Test
    void should_save_right_json_on_a_new_record() {
        final String id = "1";
        final JsonRecord jsonRecord = JsonRecord.builder()
                .id(id)
                .right_json(DECODED_VALID_JSON_1)
                .build();

        when(jsonRecordRepository.findById(id)).thenReturn(Optional.empty());
        when(jsonRecordRepository.save(jsonRecord)).thenReturn(jsonRecord);

        service.save(id, RIGHT, VALID_JSON_1);

        verify(jsonRecordRepository).findById(id);
        verify(jsonRecordRepository).save(jsonRecord);
    }

    @Test
    void should_save_letf_json_on_an_existing_record() {
        final String id = "1";
        final JsonRecord jsonRecord = JsonRecord.builder()
                .id(id)
                .right_json(DECODED_VALID_JSON_1)
                .build();
        final JsonRecord persistedJsonRecord = JsonRecord.builder()
                .id(id)
                .right_json(DECODED_VALID_JSON_1)
                .left_json(DECODED_VALID_JSON_1)
                .build();

        when(jsonRecordRepository.findById(id)).thenReturn(Optional.of(jsonRecord));
        when(jsonRecordRepository.save(persistedJsonRecord)).thenReturn(persistedJsonRecord);

        service.save(id, LEFT, VALID_JSON_1);

        verify(jsonRecordRepository).findById(id);
        verify(jsonRecordRepository).save(persistedJsonRecord);
    }

    @Test
    void should_save_right_json_on_an_existing_record() {
        final String id = "1";
        final JsonRecord jsonRecord = JsonRecord.builder()
                .id(id)
                .left_json(DECODED_VALID_JSON_1)
                .build();
        final JsonRecord persistedJsonRecord = JsonRecord.builder()
                .id(id)
                .right_json(DECODED_VALID_JSON_1)
                .left_json(DECODED_VALID_JSON_1)
                .build();

        when(jsonRecordRepository.findById(id)).thenReturn(Optional.of(jsonRecord));
        when(jsonRecordRepository.save(jsonRecord)).thenReturn(persistedJsonRecord);

        service.save(id, RIGHT, VALID_JSON_1);

        verify(jsonRecordRepository).findById(id);
        verify(jsonRecordRepository).save(persistedJsonRecord);
    }

    @Test
    void should_not_save_letf_json_when_invalid_base64_encoding() {
        final String id = "1";

        assertThrows(InvalidDataException.class, () -> service.save(id, LEFT, INVALID_JSON_3));

        verify(jsonRecordRepository, times(0)).findById(id);
        verify(jsonRecordRepository, times(0)).save(any(JsonRecord.class));
    }

    @Test
    void should_throw_InvalidDataException_and_not_save_letf_json_when_no_base64_encoding() {
        final String id = "1";

        assertThrows(InvalidDataException.class, () -> service.save(id, LEFT, INVALID_JSON_1));

        verify(jsonRecordRepository, times(0)).findById(id);
        verify(jsonRecordRepository, times(0)).save(any(JsonRecord.class));
    }

    @Test
    void should_throw_InvalidDataException_and_not_save_letf_json_when_encoded_data_is_not_a_valid_json() {
        final String id = "1";

        assertThrows(InvalidDataException.class, () -> service.save(id, LEFT, INVALID_JSON_2));

        verify(jsonRecordRepository, times(0)).findById(id);
        verify(jsonRecordRepository, times(0)).save(any(JsonRecord.class));
    }
}