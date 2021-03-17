package com.waes.test.jsondiffapi.exception;

import org.springframework.http.HttpStatus;

/**
 * InvalidDataException is thrown when some invalid data is sent to the service.
 *
 * @author Jonathan de Paula
 */
public class InvalidDataException extends BaseException {

    /**
     * InvalidDataException constructor
     *
     * @param errorMessageDetail {@link String}
     */
    public InvalidDataException(String errorMessageDetail) {
        super(errorMessageDetail, HttpStatus.BAD_REQUEST);
    }
}
