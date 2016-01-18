package com.amcg.json;


import java.util.ArrayList;
import java.util.List;

public class JsonErrorResponse {


    private List<JsonError> errors = new ArrayList<>();
    private String code;

    public JsonErrorResponse(String code, List<JsonError> errors) {
        this.errors = errors;
        this.code = code;
    }


    public void addError(JsonError jsonError) {
        errors.add(jsonError);

    }

    public List<JsonError> getErrors() {
        return errors;
    }

    public void setErrors(List<JsonError> errors) {
        this.errors = errors;
    }
}
