package com.security.buzz;

import com.techventus.server.voice.Voice;

public class GoogleVoice {
	public static void main(String[] args) {
		String userName = "buzzguardian@gmail.com";
		String pass = "gtbuzz00";

		try {
			Voice voice = new Voice(userName, pass);

			String recentMsg = voice.getUnreadSMS();
			// System.out.println( recentMsg );

			ParseSMS parseSMS = new ParseSMS();
			recentMsg = recentMsg.replace("<![CDATA[", "");
			recentMsg = recentMsg.replace("]]>", "");
			recentMsg = recentMsg.replaceAll("&", "");
			parseSMS.parse(recentMsg);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}