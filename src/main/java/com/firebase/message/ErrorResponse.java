package com.firebase.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import static com.firebase.message.ErrorCode.UNKNOWN_ERROR;

@Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public class ErrorResponse {

        private ErrorCode errorCode;
        private String message;

        public static class ErrorResponseBuilder {

            public ErrorResponseBuilder errorCode(ErrorCode e) {
                this.errorCode = e;
                return this;
            }

            public ErrorResponseBuilder errorCode(Throwable throwable) {
                if (throwable instanceof HandledException) {
                    this.errorCode = ((HandledException) throwable).getErrorCode();
                } else {
                    this.errorCode = UNKNOWN_ERROR;
                }
                return this;
            }
        }
    }