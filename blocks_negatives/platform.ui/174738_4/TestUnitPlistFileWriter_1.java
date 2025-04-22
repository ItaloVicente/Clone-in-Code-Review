		String expectedXml = getPlistStartXmlSnippet() +
				"	<key>CFBundleURLTypes</key>\n" +
				"		<array>\n" +
				getSchemeXmlSnippet("other") +
				getSchemeXmlSnippet("adt") +
				"		</array>\n" +
				"<!--comment-->\n"+
				getPlistEndXmlSnippet();
