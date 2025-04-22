
	private Reader getPlistFileReaderWithAdtSchemePlus() {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>" + //
				"<plist version=\"1.0\">\n" + //
				"<dict>\n" + //
				"	<key>CFBundleExecutable</key>\n" + //
				"		<string>eclipse</string>\n" + //
				"	<key>CFBundleURLTypes</key>\n" + //
				"		<array>\n" + //
				"			<dict>\n" + //
				"				<key>CFBundleURLName</key>\n" + //
				"					<string>AdtScheme</string>\n" + //
				"				<key>CFBundleURLSchemes</key>\n" + //
				"					<array>\n" + //
				"						<string>adt+demo</string>\n" + //
				"					</array>\n" + //
				"			</dict>\n" + //
				"		</array>\n" + //
				"</dict>\n" + //
				"</plist>\n";

		return new StringReader(xml);
	}
