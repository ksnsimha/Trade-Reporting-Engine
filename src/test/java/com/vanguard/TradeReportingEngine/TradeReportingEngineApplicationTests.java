package com.vanguard.TradeReportingEngine;

import com.vanguard.TradeReportingEngine.Repositories.EventRepository;
import com.vanguard.TradeReportingEngine.Utilities.FileUtilities;
import com.vanguard.TradeReportingEngine.Entities.EventEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.vanguard.TradeReportingEngine.Services.EventServiceImpl;


import java.io.File;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class TradeReportingEngineApplicationTests {
	@Mock
	private EventRepository eventRepository;

	@InjectMocks
	private EventServiceImpl eventService;

	@BeforeEach
	public void setup() {
		// Initialize mocks
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testParseAndSaveXmlData() throws Exception {

		List<File> xmlFiles= FileUtilities.getAllXmlFiles();

		// Call the method under test
		eventService.processXmlFiles();

		// Verify that the save method in the repository was called
		verify(eventRepository, times(xmlFiles.size())).save(any(EventEntity.class));
	}

}
