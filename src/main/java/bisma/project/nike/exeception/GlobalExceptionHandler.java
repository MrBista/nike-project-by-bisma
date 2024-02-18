package bisma.project.nike.exeception;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.support.MetaDataAccessException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);


    @ExceptionHandler( MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleErr(MethodArgumentNotValidException err) {
        logger.error("error: ",err);
        List<String> errors = err
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(res-> res.getDefaultMessage())
                .collect(Collectors.toList());

        return generateErrResponse(errors, "Something went wrong", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleStatusErr(ResponseStatusException err) {
        logger.error("error: ", err);
        HttpStatus status = (HttpStatus) err.getStatusCode();
        List<String> errors = List.of(err.getBody().getDetail());
        return generateErrResponse(errors, "Failed to get data", status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> unHandledErr(Exception e) {
        logger.error("error: ", e);
        List<String> errors = List.of(e.getMessage());
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return generateErrResponse(errors, "Internal server error", status);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> unHandleErr(Exception e) {
        logger.error("error: ", e);
        List<String> errors = List.of(e.getMessage());
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        return generateErrResponse(errors, "Internal server error", status);
    }


    public  ResponseEntity<Map<String, Object>> generateErrResponse(Object errors, String message, HttpStatus status) {
        Map<String, Object> mappingErr = new HashMap<>();
        mappingErr.put("data",null);
        mappingErr.put("message", message);
        mappingErr.put("status", status.value());
        mappingErr.put("errors", errors);
        return new ResponseEntity<>(mappingErr, status);
    }
}
