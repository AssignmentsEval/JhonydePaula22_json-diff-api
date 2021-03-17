package com.waes.test.jsondiffapi.exception;

import org.springframework.http.HttpStatus;

/**
 * JsonDataNotPresentException is thrown when Json is not present and the user tries to get the differences.
 *
 * @author Jonathan de Paula
 */
public class JsonDataNotPresentException extends BaseException {

    /**
     * JsonDataNotPresentException constructor
     *
     * @param errorMessageDetail {@link String}
     */
    public JsonDataNotPresentException(String errorMessageDetail) {
        super(errorMessageDetail, HttpStatus.BAD_REQUEST);
    }
}
