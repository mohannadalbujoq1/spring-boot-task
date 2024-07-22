package op.co.Spring_Boot_Task.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiException {

private final String message;
private final HttpStatus HttpStatus;
private final ZonedDateTime timestamp;

public ApiException(String message,
                    HttpStatus httpStatus,
                    ZonedDateTime timestamp){
    this.message = message;
    HttpStatus = httpStatus;
    this.timestamp = timestamp;
}

public String getMessage() {
    return message;
}


public HttpStatus getHttpStatus() {
    return HttpStatus;
}

public ZonedDateTime getTimestamp() {
    return timestamp;
}

}