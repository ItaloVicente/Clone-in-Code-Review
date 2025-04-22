	@Test
	public void handlesAddOnlyPlus() throws Exception {
		fileProvider.readAnswers.put(OWN_APP_PLIST_PATH, getPlistFileReader());

		registration.handleSchemes(Arrays.asList(ADT_DEMO_SCHEME_INFO), Collections.emptyList());

		assertFilePathIs(OWN_APP_PLIST_PATH);

		assertSchemeInFile("adt+demo");

		assertLsRegisterCallWithOptionAtIndex("-u", 0);
		assertLsRegisterCallWithOptionAtIndex("-r", 1);
	}

