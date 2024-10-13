package com.vanguard.TradeReportingEngine.Controllers;

import com.vanguard.TradeReportingEngine.Customization.CustomCondition;
import com.vanguard.TradeReportingEngine.Entities.EventEntity;
import com.vanguard.TradeReportingEngine.Services.EventServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.vanguard.TradeReportingEngine.Services.EventService;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@Validated
public class EventsController {
    private static final Logger logger = LoggerFactory.getLogger(EventsController.class);
    @Autowired
    private EventService eventService;

    @GetMapping("/filtered")
    public ResponseEntity<List<EventEntity>> getFilteredTransactions() {
        logger.info("Received a Get Request with default filter conditions");
        List<EventEntity> filteredTransactions = eventService.getFilteredTransactions();
        return ResponseEntity.ok(filteredTransactions);
    }

    @PostMapping("/customFiltered")
    public ResponseEntity<List<EventEntity>> getFilteredTransactions(@RequestBody List<CustomCondition> conditions) {
        logger.info("Received a Post Request with additional "+conditions.size()+" filter conditions");
        List<EventEntity> filteredTransactions = eventService.getFilteredTransactions(conditions);
        return ResponseEntity.ok(filteredTransactions);
    }

    @ExceptionHandler({javax.validation.ConstraintViolationException.class,org.hibernate.query.sqm.PathElementException.class})
    public ResponseEntity<String> handleValidationExceptions(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}