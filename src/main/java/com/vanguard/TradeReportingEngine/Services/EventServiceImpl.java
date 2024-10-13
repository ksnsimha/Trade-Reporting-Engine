package com.vanguard.TradeReportingEngine.Services;

import com.vanguard.TradeReportingEngine.Repositories.EventRepository;
import com.vanguard.TradeReportingEngine.Utilities.FileUtilities;
import com.vanguard.TradeReportingEngine.Utilities.XmlUtils;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.vanguard.TradeReportingEngine.Entities.EventEntity;
import com.vanguard.TradeReportingEngine.Entities.EventSpecification;
import jakarta.annotation.PostConstruct;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.List;
import java.math.BigDecimal;
import java.util.stream.Collectors;

@Service
@Setter
public class EventServiceImpl implements EventService{
    @Autowired
    private EventRepository eventRepository;
    private static final Logger logger = LoggerFactory.getLogger(EventServiceImpl.class);

    /**
     *
     */
    @Override
    @PostConstruct
    public void processXmlFiles(){



        List<File> xmlFiles= FileUtilities.getAllXmlFiles();
        for (File file : xmlFiles) {
            try{
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            logger.info("Parsing XML file: " + file.getName());
            logger.debug("Extracting values from XML...");

            // Parse the XML file and get a Document object
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            // Use the common method to extract values
            String buyerParty = XmlUtils.getValueFromXml("//buyerPartyReference/@href", doc);
                logger.debug("Buyer party: " + buyerParty);
            String sellerParty = XmlUtils.getValueFromXml("//sellerPartyReference/@href", doc);
                logger.debug("Seller Party: " + sellerParty);
            String premiumAmountStr = XmlUtils.getValueFromXml("//paymentAmount/amount", doc);
                logger.debug("Premium Amount: " + premiumAmountStr);
            String premiumCurrency = XmlUtils.getValueFromXml("//paymentAmount/currency", doc);
                logger.debug("Premium Currency: " + premiumCurrency);


            BigDecimal premiumAmount = BigDecimal.valueOf(Double.parseDouble(premiumAmountStr));
            logger.info("Saving extracted data to the database");

                    EventEntity eventEntity = new EventEntity();
                    eventEntity.setBuyerParty(buyerParty);
                    eventEntity.setSellerParty(sellerParty);
                    eventEntity.setPremiumAmount(premiumAmount);
                    eventEntity.setPremiumCurrency(premiumCurrency);
                    eventRepository.save(eventEntity);
                    logger.info("Extracted data is successfully saved in DB");

            } catch (Exception e) {
                logger.error("Error while parsing XML and saving data", e);
            }
        }
    }

    @Override
    public List<EventEntity> getFilteredTransactions() {

        // Dynamically build the transaction specification
        Specification<EventEntity> specification = EventSpecification.buildTransactionSpecification(
                "EMU_BANK", "AUD", "BISON_BANK", "USD", null);

        // Fetch matching transactions from the database
        List<EventEntity> transactions = eventRepository.findAll(specification);

        // Filter out transactions where seller_party and buyer_party are anagrams
        return transactions.stream()
                .filter(t -> !isAnagram(t.getSellerParty(), t.getBuyerParty()))
                .collect(Collectors.toList());
    }

    /**
     *
     * @param string1
     * @param string2
     * @return
     */
    private boolean isAnagram(String string1, String string2) {
        if (string1.length() != string2.length()) {
            return false;
        }
        Multiset<Character> multiset1 = HashMultiset.create();
        Multiset<Character> multiset2 = HashMultiset.create();
        for (int i = 0; i < string1.length(); i++) {
            multiset1.add(string1.charAt(i));
            multiset2.add(string2.charAt(i));
        }
        return multiset1.equals(multiset2);
    }
}
