package com.security.buzz;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Collection;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.techventus.server.voice.Voice;
import com.techventus.server.voice.datatypes.records.*;

public class GoogleVoice {

	String userName;
	String pass;
	Voice voice;

	GoogleVoice() {
		userName = "buzzguardian@gmail.com";
		pass = "gtbuzz00";

		try {
			voice = new Voice(userName, pass);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// Returns a list of strings. 3 consecutive strings represent a SMS
	// First string is sender number
	// Second string is sms text
	// Third string is time
	public List<String> getUnreadSMS_2() throws DocumentException {

		ArrayList<String> aList = new ArrayList<String>();

		try {

			Collection<SMSThread> smsthreads = voice.getSMSThreads();

			int sz = 0;
			for (SMSThread t : smsthreads) {
				Collection<SMS> sms = t.getAllSMS();
				sz += sms.size();
				//System.out.println( "Thread Messages: " + sms.size() );
			}
			System.out.println( "Current Messages: " + sz );

			int readCount;
			File fname = new File("readCount.txt");
			if (!fname.exists()) {
				System.out.println("Initializing !!!");
				readCount = 0;
			} else {
				Scanner sc = new Scanner(new File("readCount.txt"));
				readCount = sz - sc.nextInt();
				sc.close();
			}

			PrintWriter writer = new PrintWriter("readCount.txt");
			writer.println(sz);
			writer.close();

			int kt = 0;
			for (SMSThread t : smsthreads) {
				if (kt++ >= readCount)
					break;
				Collection<SMS> sms = t.getAllSMS();
				for (SMS s : sms) {
					System.out.println("Number: " + s.getFrom().getNumber());
					System.out.println("Text: " + s.getContent());
					System.out.println("Time: " + s.getDateTime().toString());

					aList.add(s.getFrom().getNumber());
					aList.add(s.getContent());
					aList.add(s.getDateTime().toString());
					break;
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return aList;
	}

	// Returns a list of strings. 3 consecutive strings represent a SMS
	// First string is sender number
	// Second string is sms text
	// Third string is time
	public List<String> getUnreadSMS() throws DocumentException {

		ArrayList<String> aList = new ArrayList<String>();

		try {
			String recentMsg = voice.getSMS();

			// System.out.println( recentMsg );
			PrintWriter wrt = new PrintWriter("XML.txt");
			wrt.println(recentMsg);
			wrt.close();

			recentMsg = recentMsg.replace("<![CDATA[", "");
			recentMsg = recentMsg.replace("]]>", "");
			recentMsg = recentMsg.replaceAll("&", "");

			Document document = DocumentHelper.parseText(recentMsg);
			List list = document
					.selectNodes("//div[@class='gc-message-sms-row']");
			System.out.println(list.size());
			int readCount = 0;

			File fname = new File("readCount.txt");
			if (!fname.exists()) {
				System.out.println("Initializing !!!");
				PrintWriter writer = new PrintWriter("readCount.txt");
				writer.println(readCount);
				writer.close();
			} else {
				Scanner sc = new Scanner(new File("readCount.txt"));
				readCount = list.size() - sc.nextInt();
				sc.close();
				PrintWriter writer = new PrintWriter("readCount.txt");
				writer.println(list.size());
				writer.close();
			}

			Iterator msgIterator = list.iterator();
			for (int i = 0; i < readCount && msgIterator.hasNext(); ++i) {
				Element msgElement = (Element) msgIterator.next();
				for (Iterator msgAttributeIterator = msgElement
						.elementIterator(); msgAttributeIterator.hasNext();) {
					Element msgAttributeElement = (Element) msgAttributeIterator
							.next();
					// System.out.println(msgAttributeElement.attributeValue("class")
					// + "\t" + msgAttributeElement.getStringValue().trim());
					aList.add(msgAttributeElement.getStringValue().trim());
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return aList;
	}

	public static void main(String[] args) {
		try {
			GoogleVoice googleVoice = new GoogleVoice();

			// List<String> list = googleVoice.getUnreadSMS();
			// for (int i = 0; i < list.size(); i += 3) {
			// System.out.println("Message " + (i / 3 + 1) + ":");
			// System.out.println("Number: " + list.get(i));
			// System.out.println("  Text: " + list.get(i + 1));
			// System.out.println("  Time: " + list.get(i + 2));
			// }

			List<String> list = googleVoice.getUnreadSMS_2();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}