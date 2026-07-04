package com.example.todollist.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    TODO_NOT_FOUND(404, "Todo not found"),
    INVALID_DATA(400, "Invalid data provided"),
    INTERNAL_SERVER_ERROR(500, "Internal server error");

    private final int status;
    private final String message;

    ErrorCode(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
