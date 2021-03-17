package com.waes.test.jsondiffapi.exception;

import org.springframework.http.HttpStatus;

/**
 * JsonRecordNotFoundException is thrown when Json Record is not found while trying to get the differences between Json Data..
 *
 * @author Jonathan de Paula
 */
public class JsonRecordNotFoundException extends BaseException {

    /**
     * JsonRecordNotFoundException constructor
     *
     * @param errorMessageDetail {@link String}
     */
    public JsonRecordNotFoundException(String errorMessageDetail) {
        super(errorMessageDetail, HttpStatus.BAD_REQUEST);
    }
}
