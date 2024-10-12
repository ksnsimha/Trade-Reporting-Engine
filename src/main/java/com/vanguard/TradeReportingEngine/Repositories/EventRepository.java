package com.vanguard.TradeReportingEngine.Repositories;

import com.vanguard.TradeReportingEngine.Entities.EventEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

/**
 *
 */
public interface EventRepository extends CrudRepository<EventEntity,Long> , JpaSpecificationExecutor<EventEntity> {
}
