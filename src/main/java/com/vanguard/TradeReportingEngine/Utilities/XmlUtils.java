package com.vanguard.TradeReportingEngine.Utilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import javax.xml.xpath.*;

public class XmlUtils {
    private static final Logger logger = LoggerFactory.getLogger(XmlUtils.class);

    // Common method to evaluate an XPath expression and return the result as a String
    public static String getValueFromXml(String xpathExpression, Document doc)  {

        logger.debug("Extracting values from XML for xpathExpression "+xpathExpression);
        XPathFactory xPathFactory = XPathFactory.newInstance();
        XPath xpath = xPathFactory.newXPath();
        XPathExpression expr = null;
        try {
            expr = xpath.compile(xpathExpression);
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }

        // Evaluate the expression and return the result as a string
        try {
            return (String) expr.evaluate(doc, XPathConstants.STRING);
        } catch (XPathExpressionException e) {
            throw new RuntimeException(e);
        }
    }
}
