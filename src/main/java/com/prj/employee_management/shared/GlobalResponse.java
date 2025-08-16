package com.prj.employee_management.shared;

import java.util.List;

import lombok.*;

@Data
public class GlobalResponse<T> {

    public final static String SUCCESS = "seccess";
    public final static String ERROR = "error";

    private final String status;
    private final T data;
    private final List<ErrorItem> errors;

    public record  ErrorItem(String message) {}

    public GlobalResponse(List<ErrorItem> errors){
        this.status = ERROR;
        this.data = null;
        this.errors = errors;
    }

    public GlobalResponse(T data){
        this.status = SUCCESS;
        this.data = data;
        this.errors = null;
    }

}

/*
 * 
 * format de retour
 * 
 * si on a SUCCESS
 * {
 *      status: "SUCCESS"
 *      data: [] || {}
 *      error: null
 * }
 * 
 * sin on a ERROR
 * {
 *      status: "error"
 *      data: null
 *      error: [{err1}, {err2}, {err3},...,{errN}]
 * }
 */