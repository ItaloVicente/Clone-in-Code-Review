	@Test
	public void handlesAddAndRemoveAtOncePlus() throws Exception {
		fileProvider.readAnswers.put(OWN_APP_PLIST_PATH, getPlistFileReaderWithAdtSchemePlus());

		registration.handleSchemes(Arrays.asList(OTHER_SCHEME_INFO), Arrays.asList(ADT_DEMO_SCHEME_INFO));

		assertFilePathIs(OWN_APP_PLIST_PATH);

		assertSchemeInFile("other");
		assertSchemeNotInFile("adt+demo");

		assertLsRegisterCallWithOptionAtIndex("-u", 0);
		assertLsRegisterCallWithOptionAtIndex("-r", 1);
	}

