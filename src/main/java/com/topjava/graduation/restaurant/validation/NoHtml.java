package com.topjava.graduation.restaurant.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;

@Documented
@Constraint(validatedBy = NoHtmlValidator.class)
@Target({METHOD,FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoHtml {
    String message() default "Unsafe html content";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
