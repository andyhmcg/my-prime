package com.amcg.exception;

import com.amcg.json.JsonError;
import com.amcg.json.JsonErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@ControllerAdvice
public class GlobalExceptionHandler {



    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public JsonErrorResponse handleBindingException(HttpServletRequest req, BindException ex) {

        List<JsonError> messages = processBindException(ex);
        return new JsonErrorResponse(HttpStatus.BAD_REQUEST.name(),messages);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public JsonErrorResponse handleHttpMessageNotReadableException(HttpServletRequest req, MethodArgumentTypeMismatchException ex) {

        JsonError error = new JsonError(ex.getName(), ex.getMessage(), ex.getValue().toString());
        return new JsonErrorResponse(HttpStatus.BAD_REQUEST.name(), Collections.singletonList(error));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public JsonErrorResponse handleThrowableRequest(HttpServletRequest req, Throwable ex) {
        JsonError error = new JsonError("Internal Server Error", ex.getMessage());
        return new JsonErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.name(), Collections.singletonList(error));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidRequestException.class)
    @ResponseBody
    public JsonErrorResponse handleInvaliedRequestException(HttpServletRequest req, InvalidRequestException ex) {
        JsonError error = new JsonError("Invalid Request", ex.getMessage());
        return new JsonErrorResponse(HttpStatus.BAD_REQUEST.name(), Collections.singletonList(error));
    }



    private List<JsonError> processBindException(BindException ex){

        List<ObjectError> errors = ex.getAllErrors();

        return errors.stream().map(e -> {
                    if (e instanceof FieldError) {
                        FieldError fieldError = (FieldError) e;
                        return new JsonError(fieldError.getField(), fieldError.getDefaultMessage(), fieldError.getRejectedValue().toString());
                    } else {
                        return new JsonError(e.getObjectName(), e.getDefaultMessage());
                    }

                }
        ).collect(Collectors.toList());

    }
}