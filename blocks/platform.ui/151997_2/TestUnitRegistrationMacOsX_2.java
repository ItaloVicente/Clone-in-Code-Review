	@Test
	public void handlesRemoveOnlyPlus() throws Exception {
		fileProvider.readAnswers.put(OWN_APP_PLIST_PATH, getPlistFileReaderWithAdtSchemePlus());

		registration.handleSchemes(Collections.emptyList(), Arrays.asList(ADT_DEMO_SCHEME_INFO));

		assertFilePathIs(OWN_APP_PLIST_PATH);

		assertSchemeNotInFile("adt+demo");

		assertLsRegisterCallWithOptionAtIndex("-u", 0);
		assertLsRegisterCallWithOptionAtIndex("-r", 1);
	}

