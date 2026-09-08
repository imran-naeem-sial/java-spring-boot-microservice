package com.app.ecom.Utils;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    private boolean success;
    private String message;
    private Object data;
    private int statusCode;
    private LocalDateTime timestamp;

    public Response() {
        this.timestamp = LocalDateTime.now();
        this.statusCode = 200;
    }

    public Response(boolean success, String message, Object data, int statusCode) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.statusCode = statusCode;
        this.timestamp = LocalDateTime.now();
    }

    public Response(boolean success, String message, Object data) {
        this(success, message, data, 200);
    }

    // Success responses
    public static Response getOkRequest(String message, Object data) {
        return new Response(true, message, data, 200);
    }

    // Success responses
    public static Response getOkRequest(Object data) {
        return new Response(true, "O.K.", data, 200);
    }

    public static Response getCreatedRequest(String message, Object data) {
        return new Response(true, message, data, 201);
    }

    // Error responses
    public static Response getBadRequest(String message) {
        return new Response(false, message, null, 400);
    }

    public static Response getUnauthorizedRequest(String message) {
        return new Response(false, message, null, 401);
    }

    public static Response getForbiddenRequest(String message) {
        return new Response(false, message, null, 403);
    }

    public static Response getNotFoundRequest(String message) {
        return new Response(false, message, null, 404);
    }

    public static Response getIsRequest(String message) {
        return new Response(false, message, null, 500);
    }

    public static Response getServiceUnavailableRequest(String message) {
        return new Response(false, message, null, 503);
    }

    // Generic custom response
    public static Response error(String message, int statusCode) {
        return new Response(false, message, null, statusCode);
    }

    public static Response error(String message, int statusCode, Object data) {
        return new Response(false, message, data, statusCode);
    }

    public static Response custom(boolean success, String message, int statusCode, Object data) {
        return new Response(success, message, data, statusCode);
    }
}