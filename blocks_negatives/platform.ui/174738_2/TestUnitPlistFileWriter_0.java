		String xml = getPlistStartXmlSnippet() +
				"	<key>CFBundleURLTypes</key>\n" +
				"		<array>\n" +
				getSchemeXmlSnippet("other") +
				"		</array>\n" +
				"<!--comment-->\n"+
				getPlistEndXmlSnippet();
