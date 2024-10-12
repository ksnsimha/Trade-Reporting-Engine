package services;

import Repositories.EventRepository;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import entity.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.util.List;
import java.math.BigDecimal;

public class EventService {
    @Autowired
    private EventRepository eventRepository;

    /**
     *
     * @param xmlFiles
     */
    public void processXmlFiles(List<File> xmlFiles) {
        for (File file : xmlFiles) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            try {
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(file);
                XPath xPath = XPathFactory.newInstance().newXPath();

                String buyerParty;
                buyerParty = xPath.evaluate("//buyerPartyReference/@href", document);
                String sellerParty = xPath.evaluate("//sellerPartyReference/@href", document);
                BigDecimal premiumAmount = new BigDecimal(xPath.evaluate("//paymentAmount/amount", document));
                String premiumCurrency = xPath.evaluate("//paymentAmount/currency", document);

                // Check filter criteria
                if ((sellerParty.equals("EMU_BANK") && premiumCurrency.equals("AUD")) ||
                        (sellerParty.equals("BISON_BANK") && premiumCurrency.equals("USD")) &&
                                !isAnagram(sellerParty, buyerParty)) {
                    Event event = new Event();
                    event.setBuyerParty(buyerParty);
                    event.setSellerParty(sellerParty);
                    event.setPremiumAmount(premiumAmount);
                    event.setPremiumCurrency(premiumCurrency);
                    eventRepository.save(event);
                }
            } catch (Exception e) {
                // Handle exceptions
            }
        }
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
