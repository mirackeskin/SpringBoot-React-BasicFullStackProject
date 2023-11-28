package com.basicproject.api.errors;

import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
public class ApiError {

    int statusCode;

    String message;

    String path;

    long timeStamp = new Date().getTime();

    Map<String,String> validationErrors = new HashMap<>();

}
