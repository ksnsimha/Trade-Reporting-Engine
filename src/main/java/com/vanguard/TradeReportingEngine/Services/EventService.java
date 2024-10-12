package com.vanguard.TradeReportingEngine.Services;

import com.vanguard.TradeReportingEngine.Entities.EventEntity;

import java.util.List;

public interface EventService {

    public void processXmlFiles();
   public List<EventEntity> getFilteredTransactions();

}
