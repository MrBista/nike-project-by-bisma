package bisma.project.nike.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class CommonResponse {
    public static ResponseEntity<Object> generateResponse(Object data, String message, HttpStatus status){
        Map<String, Object> mappingResponse = new HashMap<>();
        mappingResponse.put("data", data);
        mappingResponse.put("message" , message);
        mappingResponse.put("status", status.value());

        return new ResponseEntity<>(mappingResponse, status);
    }
}
