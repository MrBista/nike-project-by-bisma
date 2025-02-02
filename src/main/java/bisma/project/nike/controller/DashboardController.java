package bisma.project.nike.controller;

import bisma.project.nike.dto.response.CommonResponse;
import bisma.project.nike.repository.CategoryRepository;
import bisma.project.nike.repository.CategorySubRepository;
import bisma.project.nike.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/dashboards")
@CrossOrigin(origins = "http://localhost:8000", maxAge = 3600, allowCredentials="true")

public class DashboardController {

    public static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategorySubRepository categorySubRepository;

    @Autowired
    CategoryRepository categoryRepository;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> getDashboardProductCategory (@RequestParam(required = false) String beginDateInMs,
                                                               @RequestParam(required = false) String endDateInMs ) {
            LocalDateTime startDateTime = LocalDate.now().withDayOfYear(1).atStartOfDay();

            LocalDateTime endDateTime = LocalDate.now().atStartOfDay();

            long startEpochMs = startDateTime.toEpochSecond(ZoneOffset.UTC) * 1000;
            long endEpochMs = endDateTime.toEpochSecond(ZoneOffset.UTC) * 1000;


            if (beginDateInMs != null) {
                startEpochMs = Long.parseLong(beginDateInMs);
            }

            if (endDateInMs != null) {
                endEpochMs = Long.parseLong(endDateInMs);
            }



            long countData = productRepository.countByCreatedAtBetween(startEpochMs, endEpochMs);
            long countDataCategory = categoryRepository.countByCreatedAtBetween(startEpochMs, endEpochMs);
            Map<String, Object> res = new HashMap<>();
            res.put("productCount", countData);
            res.put("categoryCount", countDataCategory);
//        productRepository.countByDateBetween();
        return CommonResponse.generateResponse(res, "successfully get all data", HttpStatus.OK);
    }

}
