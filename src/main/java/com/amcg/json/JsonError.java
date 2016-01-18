package com.amcg.json;


public class JsonError {
    private final String id;
    private final String message;
    private final String value;

    public String getValue() {
        return value;
    }
    public String getMessage() {
        return message;
    }

    public String getId() {
        return id;
    }

    public JsonError(String code, String message, String value) {
        this.id = code;
        this.message = message;
        this.value = value;
    }

    public JsonError(String code, String message) {
        this.id = code;
        this.message = message;
        this.value = "";

    }


}

