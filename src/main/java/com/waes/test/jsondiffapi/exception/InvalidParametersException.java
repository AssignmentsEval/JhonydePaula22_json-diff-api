package com.waes.test.jsondiffapi.exception;

import org.springframework.http.HttpStatus;

/**
 * InvalidParametersException is thrown when some invalid param is sent to the service.
 *
 * @author Jonathan de Paula
 */
public class InvalidParametersException extends BaseException {

    /**
     * InvalidParametersException constructor
     *
     * @param errorMessageDetail {@link String}
     */
    public InvalidParametersException(String errorMessageDetail) {
        super(errorMessageDetail, HttpStatus.BAD_REQUEST);
    }
}
