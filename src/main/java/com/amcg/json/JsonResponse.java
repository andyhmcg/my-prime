package com.amcg.json;

public class JsonResponse<T> {

    private T data;

    public JsonResponse(T data){

        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


}
