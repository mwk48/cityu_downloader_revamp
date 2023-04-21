package com.example.downloader.exceptions;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Builder
@AllArgsConstructor
@Getter
@ToString
public class ApiException {

    private final String message;

    private final long timestamp;

    private final HttpStatus status;

    private final Throwable throwable;


}
