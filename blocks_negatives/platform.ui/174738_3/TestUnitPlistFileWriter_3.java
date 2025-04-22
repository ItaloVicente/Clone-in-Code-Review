		String expectedXml = getPlistStartXmlSnippet() +
				"	<key>CFBundleURLTypes</key>\n" +
				"		<array>\n" +
				getSchemeXmlSnippet("other") +
				"<!--comment-->\n"+
				getSchemeXmlSnippet("adt") +
				"		</array>\n" +
				getPlistEndXmlSnippet();
