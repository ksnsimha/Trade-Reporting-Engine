package com.vanguard.TradeReportingEngine.Services;

import com.vanguard.TradeReportingEngine.Customization.CustomCondition;
import com.vanguard.TradeReportingEngine.Entities.EventEntity;

import java.util.List;

public interface EventService {

    public void processXmlFiles();
    public List<EventEntity> getFilteredTransactions();
    public List<EventEntity> getFilteredTransactions(List<CustomCondition> customConditionList);

}
