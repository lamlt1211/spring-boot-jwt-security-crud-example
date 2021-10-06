package com.savvycom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class APIResponse<T> {
    @JsonProperty("status")
    private int status;

    @JsonProperty("message")
    private String message;

    @JsonProperty("data")
    private T data;

    public APIResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public APIResponse(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }
}
