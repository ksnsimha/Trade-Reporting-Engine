package com.vanguard.TradeReportingEngine;

import com.vanguard.TradeReportingEngine.Repositories.EventRepository;
import com.vanguard.TradeReportingEngine.Utilities.FileUtilities;
import com.vanguard.TradeReportingEngine.Entities.EventEntity;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.*;

import com.vanguard.TradeReportingEngine.Services.EventServiceImpl;

import org.springframework.context.annotation.PropertySource;


import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@DataJpaTest
@PropertySource("classpath:application-test.properties")
class TradeReportingEngineApplicationTests {
     @Autowired
	 private EventRepository eventRepository;
	 EventServiceImpl eventService = new EventServiceImpl();



	@BeforeEach
	public void setup() {
		// Initialize mocks
		MockitoAnnotations.openMocks(this);
		eventService.setEventRepository(eventRepository);
	}



	/**
	 * The seller_party is EMU_BANK and the premium_currency is AUD , No Anagram
	 * Expected Result : eventService.getFilteredTransactions should return 1 transaction
	 * @throws Exception
	 */
	@Test
	public void testFilterCondition1() throws Exception {
		EventEntity eventEntity = new EventEntity();
		eventEntity.setBuyerParty("buyerParty");
		eventEntity.setSellerParty("EMU_BANK");
		eventEntity.setPremiumAmount(BigDecimal.valueOf(200.0000));
		eventEntity.setPremiumCurrency("AUD");
		eventRepository.save(eventEntity);
		List<EventEntity> filtered = eventService.getFilteredTransactions();
		assertThat(filtered.size()).isEqualTo(1);

	}
	/**
	 * The seller_party is EMU_BANK and the premium_currency is USD,No Anagram
	 * Expected Result : eventService.getFilteredTransactions should return 0 transaction
	 * @throws Exception
	 */
	@Test
	public void testFilterCondition1Negative() throws Exception {
		EventEntity eventEntity = new EventEntity();
		eventEntity.setBuyerParty("buyerParty");
		eventEntity.setSellerParty("EMU_BANK");
		eventEntity.setPremiumAmount(BigDecimal.valueOf(200.0000));
		eventEntity.setPremiumCurrency("USD");
		eventRepository.save(eventEntity);
		List<EventEntity> filtered = eventService.getFilteredTransactions();
		assertThat(filtered.size()).isEqualTo(0);

	}
	/**
	 * The seller_party is BISON_BANK and the premium_currency is USD,No Anagram
	 * Expected Result : eventService.getFilteredTransactions should return 1 transaction
	 * @throws Exception
	 */
	@Test
	public void testFilterCondition2() throws Exception {
		EventEntity eventEntity = new EventEntity();
		eventEntity.setBuyerParty("buyerParty");
		eventEntity.setSellerParty("BISON_BANK");
		eventEntity.setPremiumAmount(BigDecimal.valueOf(200.0000));
		eventEntity.setPremiumCurrency("USD");
		eventRepository.save(eventEntity);
		List<EventEntity> filtered = eventService.getFilteredTransactions();
		assertThat(filtered.size()).isEqualTo(1);

	}
	/**
	 * The seller_party is BISON_BANK and the premium_currency is AUD,No Anagram
	 * Expected Result : eventService.getFilteredTransactions should return 0 transaction
	 * @throws Exception
	 */
	@Test
	public void testFilterCondition2Negative() throws Exception {
		EventEntity eventEntity = new EventEntity();
		eventEntity.setBuyerParty("buyerParty");
		eventEntity.setSellerParty("BISON_BANK");
		eventEntity.setPremiumAmount(BigDecimal.valueOf(200.0000));
		eventEntity.setPremiumCurrency("AUD");
		eventRepository.save(eventEntity);
		List<EventEntity> filtered = eventService.getFilteredTransactions();
		assertThat(filtered.size()).isEqualTo(0);

	}
	/**
	 * The seller_party is EMU_BANK and the premium_currency is AUD ,  Anagram
	 * Expected Result : eventService.getFilteredTransactions should return 0 transaction
	 * @throws Exception
	 */
	@Test
	public void testFilterCondition1WithAnagram() throws Exception {
		EventEntity eventEntity = new EventEntity();
		eventEntity.setBuyerParty("BNAK_UME");
		eventEntity.setSellerParty("EMU_BANK");
		eventEntity.setPremiumAmount(BigDecimal.valueOf(200.0000));
		eventEntity.setPremiumCurrency("AUD");
		eventRepository.save(eventEntity);
		List<EventEntity> filtered = eventService.getFilteredTransactions();
		assertThat(filtered.size()).isEqualTo(0);

	}
	/**
	 * The seller_party is BISON_BANK and the premium_currency is USD, Anagram
	 * Expected Result : eventService.getFilteredTransactions should return 0 transaction
	 * @throws Exception
	 */
	@Test
	public void testFilterCondition2WithAnagram() throws Exception {
		EventEntity eventEntity = new EventEntity();
		eventEntity.setBuyerParty("ANKB_NOISB");
		eventEntity.setSellerParty("BISON_BANK");
		eventEntity.setPremiumAmount(BigDecimal.valueOf(200.0000));
		eventEntity.setPremiumCurrency("USD");
		eventRepository.save(eventEntity);
		List<EventEntity> filtered = eventService.getFilteredTransactions();
		assertThat(filtered.size()).isEqualTo(0);

	}

	/**
	 * 5 Events are added. Out of which 3 doesn't match either one or all of the filter conditions
	 * Expected Result : eventService.getFilteredTransactions should return 2 transaction
	 * @throws Exception
	 */
	@Test
	public void testMultipleEvents() throws Exception {
		EventEntity eventEntity1 = new EventEntity();
		eventEntity1.setBuyerParty("BuyerParty");
		eventEntity1.setSellerParty("BISON_BANK");
		eventEntity1.setPremiumAmount(BigDecimal.valueOf(200.0000));
		eventEntity1.setPremiumCurrency("USD");
		eventRepository.save(eventEntity1);

		EventEntity eventEntity2 = new EventEntity();
		eventEntity2.setBuyerParty("buyerParty");
		eventEntity2.setSellerParty("EMU_BANK");
		eventEntity2.setPremiumAmount(BigDecimal.valueOf(400.0000));
		eventEntity2.setPremiumCurrency("AUD");
		eventRepository.save(eventEntity2);

		EventEntity eventEntity3 = new EventEntity();
		eventEntity3.setBuyerParty("BuyerParty");
		eventEntity3.setSellerParty("BISON_BANK");
		eventEntity3.setPremiumAmount(BigDecimal.valueOf(500.0000));
		eventEntity3.setPremiumCurrency("AUD");
		eventRepository.save(eventEntity3);

		EventEntity eventEntity4 = new EventEntity();
		eventEntity4.setBuyerParty("buyerParty");
		eventEntity4.setSellerParty("EMU_BANK");
		eventEntity4.setPremiumAmount(BigDecimal.valueOf(400.0000));
		eventEntity4.setPremiumCurrency("USD");
		eventRepository.save(eventEntity4);

		EventEntity eventEntity5 = new EventEntity();
		eventEntity5.setBuyerParty("EMU_BANK");
		eventEntity5.setSellerParty("EMU_BANK");
		eventEntity5.setPremiumAmount(BigDecimal.valueOf(400.0000));
		eventEntity5.setPremiumCurrency("USD");
		eventRepository.save(eventEntity5);

		List<EventEntity> filtered = eventService.getFilteredTransactions();
		assertThat(filtered.size()).isEqualTo(2);

	}


	@Test
	public void testSaveAndRetrieveTransaction() {
		// Create a new EventEntity object
		EventEntity transaction = new EventEntity();
		transaction.setBuyerParty("ABC Corp");
		transaction.setSellerParty("XYZ Ltd");
		transaction.setPremiumAmount(new BigDecimal("1000.50"));
		transaction.setPremiumCurrency("USD");

		// Persist the transaction using the repository
		EventEntity savedTransaction = eventRepository.save(transaction);

		// Retrieve the transaction by its ID
		EventEntity foundTransaction = eventRepository.findById(savedTransaction.getId()).orElse(null);

		// Assertions to verify that the saved and retrieved entities match
		assertThat(foundTransaction).isNotNull();
		assertThat(foundTransaction.getId()).isEqualTo(savedTransaction.getId());
		assertThat(foundTransaction.getBuyerParty()).isEqualTo("ABC Corp");
		assertThat(foundTransaction.getSellerParty()).isEqualTo("XYZ Ltd");
		assertThat(foundTransaction.getPremiumAmount()).isEqualTo(new BigDecimal("1000.50"));
		assertThat(foundTransaction.getPremiumCurrency()).isEqualTo("USD");
	}

	/**
	 * The seller_party is EMU_BANK and the premium_currency is AUD ,Buyer Party is VANGUARD No Anagram
	 * Expected Result : eventService.getFilteredTransactionsWithAdditionalConditions should return 0 transaction
	 * @throws Exception
	 */
	@Test
	public void testFilterCondition1WithAdditionalConditions() throws Exception {
		EventEntity eventEntity = new EventEntity();
		eventEntity.setBuyerParty("VANGUARD");
		eventEntity.setSellerParty("EMU_BANK");
		eventEntity.setPremiumAmount(BigDecimal.valueOf(200.0000));
		eventEntity.setPremiumCurrency("AUD");
		eventRepository.save(eventEntity);
		List<EventEntity> filtered = eventService.getFilteredTransactionsWithAdditionalConditions();
		assertThat(filtered.size()).isEqualTo(0);

	}

	/**
	 * The seller_party is EMU_BANK and the premium_currency is AUD ,Buyer Party is not VANGUARD No Anagram
	 * Expected Result : eventService.getFilteredTransactionsWithAdditionalConditions should return 1 transaction
	 * @throws Exception
	 */
	@Test
	public void PositiveTestcaseWithAdditionalConditions() throws Exception {
		EventEntity eventEntity = new EventEntity();
		eventEntity.setBuyerParty("BuyerParty");
		eventEntity.setSellerParty("EMU_BANK");
		eventEntity.setPremiumAmount(BigDecimal.valueOf(200.0000));
		eventEntity.setPremiumCurrency("AUD");
		eventRepository.save(eventEntity);
		List<EventEntity> filtered = eventService.getFilteredTransactionsWithAdditionalConditions();
		assertThat(filtered.size()).isEqualTo(1);

	}


}
