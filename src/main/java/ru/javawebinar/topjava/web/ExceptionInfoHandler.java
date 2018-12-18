package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.ErrorInfo;
import ru.javawebinar.topjava.util.exception.ErrorType;
import ru.javawebinar.topjava.util.exception.IllegalRequestDataException;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;

import static ru.javawebinar.topjava.util.exception.ErrorType.*;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class ExceptionInfoHandler {
    private static Logger log = LoggerFactory.getLogger(ExceptionInfoHandler.class);

    //  http://stackoverflow.com/a/22358422/548473
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(NotFoundException.class)
    public ErrorInfo handleError(HttpServletRequest req, NotFoundException e) {
        return logAndGetErrorInfo(req, e, false, DATA_NOT_FOUND);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorInfo conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        if (rootCause.getMessage().contains("unique_email")) {
            return logAndGetErrorInfo(req, rootCause, true, ErrorType.DATA_ERROR, "User with this email already exists");
        }
        if (rootCause.getMessage().contains("unique_user_datetime")) {
            return logAndGetErrorInfo(req, rootCause, true, ErrorType.DATA_ERROR, "Meal with this date and time already exists");
        }
        return logAndGetErrorInfo(req, e, true, DATA_ERROR);
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    @ExceptionHandler({IllegalRequestDataException.class, MethodArgumentTypeMismatchException.class,
                       HttpMessageNotReadableException.class})
    public ErrorInfo illegalRequestDataError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, false, VALIDATION_ERROR);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorInfo handleError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e, true, APP_ERROR);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({BindException.class})
    public ErrorInfo handleBindingValidationError(HttpServletRequest req, BindException e) {
        return logAndGetBindingValidationErrorInfo(req, e, true, ErrorType.VALIDATION_ERROR);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ErrorInfo handleArgumentValidationError(HttpServletRequest req, MethodArgumentNotValidException e) {
        return logAndGetArgumentValidationErrorInfo(req, e, true, ErrorType.VALIDATION_ERROR);
    }

//    https://stackoverflow.com/questions/538870/should-private-helper-methods-be-static-if-they-can-be-static
    private static ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e,
                                                boolean logException, ErrorType errorType) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
        logException(req, logException, errorType, rootCause);
        return new ErrorInfo(req.getRequestURL(), errorType, ValidationUtil.getMessage(rootCause));
    }

    private static ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Throwable rootCause, boolean logException,
                                                ErrorType errorType, String message) {
        logException(req, logException, errorType, rootCause);
        return new ErrorInfo(req.getRequestURL(), errorType, message);
    }

    private static ErrorInfo logAndGetBindingValidationErrorInfo(HttpServletRequest req, BindException bindEx,
                                                                 boolean logException, ErrorType errorType) {
        logException(req, logException, errorType, bindEx);
        return new ErrorInfo(req.getRequestURL(), errorType, ValidationUtil.getBindingErrorsMessages(bindEx.getBindingResult()));
    }

    private static ErrorInfo logAndGetArgumentValidationErrorInfo(HttpServletRequest req, MethodArgumentNotValidException methEx,
                                                                  boolean logException, ErrorType errorType) {
        logException(req, logException, errorType, methEx);
        return new ErrorInfo(req.getRequestURL(), errorType, ValidationUtil.getBindingErrorsMessages(methEx.getBindingResult()));
    }

    private static void logException(HttpServletRequest req, boolean logException, ErrorType errorType, Throwable rootCause) {
        if (logException) {
            log.error(errorType + " at request " + req.getRequestURL(), rootCause);
        } else {
            log.warn("{} at request  {}: {}", errorType, req.getRequestURL(), rootCause.toString());
        }
    }
}