package com.waes.test.jsondiffapi.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.waes.test.jsondiffapi.exception.InvalidDataException;
import com.waes.test.jsondiffapi.model.entity.JsonRecord;
import com.waes.test.jsondiffapi.repository.JsonRecordRepository;
import com.waes.test.jsondiffapi.service.JsonRecordService;
import com.waes.test.model.SideEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Base64;

import static com.waes.test.jsondiffapi.constants.AppLogMessagesConstants.DEFAULT_LOG_ERROR_MESSAGE;
import static com.waes.test.jsondiffapi.constants.AppLogMessagesConstants.JSON_RECORD_JSON_DATA_ADDED_LOG_MESSAGE;
import static com.waes.test.jsondiffapi.constants.AppLogMessagesConstants.JSON_RECORD_RETRIEVED_LOG_MESSAGE;
import static com.waes.test.jsondiffapi.constants.AppLogMessagesConstants.JSON_RECORD_SAVED_LOG_MESSAGE;
import static com.waes.test.jsondiffapi.constants.ExceptionConstants.INVALID_BASE64_ENCODING_EXCEPTION_MESSAGE;
import static com.waes.test.jsondiffapi.constants.ExceptionConstants.INVALID_JSON_FORMAT_EXCEPTION_MESSAGE;
import static com.waes.test.model.SideEnum.RIGHT;

/**
 * JsonRecordServiceImpl implements {@link JsonRecordService}
 * Performs operations related to saving a {@link JsonRecord} into the database
 *
 * @author Jonathan de Paula
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class JsonRecordServiceImpl implements JsonRecordService {

    private final ObjectMapper mapper;
    private final JsonRecordRepository jsonRecordRepository;

    /**
     * Perform all operations needed to save the {@link JsonRecord} into the database
     *
     * @param id          {@link String}
     * @param side        {@link SideEnum}
     * @param encodedData Base64 Encoded String converted to byte[]
     */
    @Override
    public void save(final String id, final SideEnum side, final byte[] encodedData) {
        final byte[] decodedData = decodeBase64(encodedData);
        validateIfDecodedDataIsInAValidJsonFormat(decodedData);
        final JsonRecord jsonRecord = getJsonRecord(id);
        addJsonDataToJsonRecord(jsonRecord, side, decodedData);
        persistJsonRecord(jsonRecord);
    }

    /**
     * Verify if encodedData is in a valid Base64 encoding
     *
     * @param encodedData byte[]
     * @return byte[]
     */
    private byte[] decodeBase64(final byte[] encodedData) {
        try {
            return Base64.getDecoder().decode(encodedData);
        } catch (IllegalArgumentException exception) {
            log.error(DEFAULT_LOG_ERROR_MESSAGE, exception.getMessage(), exception.getStackTrace());
            throw new InvalidDataException(INVALID_BASE64_ENCODING_EXCEPTION_MESSAGE);
        }
    }

    /**
     * Validate if decoded data is in a valid Json Format
     *
     * @param decodedData byte[]
     */
    private void validateIfDecodedDataIsInAValidJsonFormat(final byte[] decodedData) {
        try {
            mapper.readTree(decodedData);
        } catch (IOException exception) {
            log.error(DEFAULT_LOG_ERROR_MESSAGE, exception.getMessage(), exception.getStackTrace());
            throw new InvalidDataException(INVALID_JSON_FORMAT_EXCEPTION_MESSAGE);
        }
    }

    /**
     * Search for a {@link JsonRecord} on the data base. If not found, create a new one with the id provided.
     *
     * @param id {@link String}
     * @return {@link JsonRecord}
     */
    private JsonRecord getJsonRecord(final String id) {
        JsonRecord jsonRecord = jsonRecordRepository.findById(id).orElse(JsonRecord.builder().id(id).build());
        log.info(JSON_RECORD_RETRIEVED_LOG_MESSAGE, jsonRecord);
        return jsonRecord;
    }

    /**
     * Add Json data to {@link JsonRecord} on the side defined by the {@link SideEnum}
     *
     * @param jsonRecord  {@link JsonRecord}
     * @param side        {@link SideEnum}
     * @param decodedData byte[]
     */
    private void addJsonDataToJsonRecord(final JsonRecord jsonRecord, final SideEnum side, final byte[] decodedData) {
        log.info(JSON_RECORD_JSON_DATA_ADDED_LOG_MESSAGE, side, jsonRecord);
        if (RIGHT.equals(side)) {
            jsonRecord.setRight_json(decodedData);
            return;
        }
        jsonRecord.setLeft_json(decodedData);
    }

    /**
     * Persists the {@link JsonRecord} on the database.
     *
     * @param jsonRecord {@link JsonRecord}
     */
    private void persistJsonRecord(final JsonRecord jsonRecord) {
        final JsonRecord persistedJsonRecord = jsonRecordRepository.save(jsonRecord);
        log.info(JSON_RECORD_SAVED_LOG_MESSAGE, persistedJsonRecord);
    }
}
