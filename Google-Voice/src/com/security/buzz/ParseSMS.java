package com.security.buzz;

import java.util.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class ParseSMS {

	public void parse(String sms) throws DocumentException {

		// System.out.println( sms + " PRINTED SMS " );

		Document document = DocumentHelper.parseText(sms);

		List list = document.selectNodes("//div[@class='gc-message-sms-row']");

		System.out.println(list.size());

		for (Iterator msgIterator = list.iterator(); msgIterator.hasNext();) {

			Element msgElement = (Element) msgIterator.next();

			for (Iterator msgAttributeIterator = msgElement.elementIterator(); msgAttributeIterator
					.hasNext();) {

				Element msgAttributeElement = (Element) msgAttributeIterator
						.next();

				System.out.println(msgAttributeElement.attributeValue("class")
						+ "\t" + msgAttributeElement.getStringValue().trim());
			}
		}
	}
}