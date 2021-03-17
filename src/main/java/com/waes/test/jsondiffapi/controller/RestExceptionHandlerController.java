package com.waes.test.jsondiffapi.controller;

import com.waes.test.jsondiffapi.exception.BaseException;
import com.waes.test.model.ErrorDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.waes.test.jsondiffapi.constants.AppLogMessagesConstants.DEFAULT_LOG_ERROR_MESSAGE;
import static com.waes.test.jsondiffapi.constants.ExceptionConstants.DEFAULT_ERROR_MESSAGE;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

/**
 * Controller Advice to handle exceptions throw on Controllers.
 *
 * @author Jonathan de Paula
 */
@ControllerAdvice
@Slf4j
public class RestExceptionHandlerController extends ResponseEntityExceptionHandler {

    /**
     * Exception handler to handle all BaseExceptions that may be thrown
     *
     * @param ex {@link BaseException}
     * @return {@link ResponseEntity} of {@link ErrorDTO}
     */
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorDTO> handleException(BaseException ex) {
        log.error(DEFAULT_LOG_ERROR_MESSAGE, ex.getMessage(), ex.getStackTrace());
        final ErrorDTO error = new ErrorDTO().message(ex.getErrorMessageDetail());
        return ResponseEntity.status(ex.getHttpStatus()).body(error);
    }

    /**
     * Exception handler to handle any unexpected exception that may be thrown
     *
     * @param ex {@link Exception}
     * @return {@link ResponseEntity} of {@link ErrorDTO}
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleException(Exception ex) {
        log.error(DEFAULT_LOG_ERROR_MESSAGE, ex.getMessage(), ex.getStackTrace());
        final ErrorDTO error = new ErrorDTO().message(DEFAULT_ERROR_MESSAGE);
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(error);
    }
}
