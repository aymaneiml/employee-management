package com.prj.employee_management.shared;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CustomResponseException extends RuntimeException{

    private int statusCode;
    private String message;

    public static CustomResponseException ResourceNotFound(String message){
        return new CustomResponseException(404, message);
    }

    public static CustomResponseException AlreadyExists(String message){
        return new CustomResponseException(409, message);
    }

    public static CustomResponseException BadCredentials() {
        return new CustomResponseException(404, "Bad Credentials");
    }
}