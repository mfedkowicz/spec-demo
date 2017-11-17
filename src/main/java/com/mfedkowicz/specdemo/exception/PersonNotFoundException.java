package com.mfedkowicz.specdemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Mateusz Fedkowicz
 **/
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Person with given id not found")
public class PersonNotFoundException extends RuntimeException {
}
