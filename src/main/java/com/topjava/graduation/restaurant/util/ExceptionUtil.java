package com.topjava.graduation.restaurant.util;

import com.topjava.graduation.restaurant.exception.InvalidRequestException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public abstract  class ExceptionUtil {

    private ExceptionUtil() {
    }

    public static void throwExceptionIfBindingResultHasErrors(BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
            var stringBuilder = new StringBuilder();

            fieldErrorList
                    .forEach(fieldError -> stringBuilder
                            .append("Field name: ")
                            .append(fieldError.getField())
                            .append(". ")
                            .append(" Message: ")
                            .append(fieldError.getDefaultMessage())
                            .append(". "));

            throw new InvalidRequestException(stringBuilder.toString().trim());
        }
    }
}
