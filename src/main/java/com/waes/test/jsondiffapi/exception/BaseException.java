package com.waes.test.jsondiffapi.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Base exception that will be extended by all other exceptions in this project.
 *
 * @author Jonathan de Paula
 */
@Getter
public class BaseException extends RuntimeException {

    private final String errorMessageDetail;
    private final HttpStatus httpStatus;

    /**
     * BaseException constructor.
     *
     * @param errorMessageDetail {@link String}
     * @param httpStatus         {@link HttpStatus}
     */
    BaseException(String errorMessageDetail, HttpStatus httpStatus) {
        super(errorMessageDetail);
        this.errorMessageDetail = errorMessageDetail;
        this.httpStatus = httpStatus;
    }
}
