		String xml = getPlistStartXmlSnippet() +
				"	<key>CFBundleURLTypes</key>\n" +
				"		<array>\n" +
				getSchemeXmlSnippet("other") +
				"<!--comment-->"+
				"		</array>\n" +
				getPlistEndXmlSnippet();
