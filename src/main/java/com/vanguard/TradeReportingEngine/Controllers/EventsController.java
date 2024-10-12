package com.vanguard.TradeReportingEngine.Controllers;

import com.vanguard.TradeReportingEngine.Entities.EventEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vanguard.TradeReportingEngine.Services.EventService;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class EventsController {

    @Autowired
    private EventService eventService;

    @GetMapping("/filtered")
    public ResponseEntity<List<EventEntity>> getFilteredTransactions() {
        List<EventEntity> filteredTransactions = eventService.getFilteredTransactions();
        return ResponseEntity.ok(filteredTransactions);
    }
}