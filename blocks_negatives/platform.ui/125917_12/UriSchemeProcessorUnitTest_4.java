	@Test
	public void callsFirstOfTwoHandlersForSameScheme() throws Exception {
		HandlerMock secondAbcHandler = new HandlerMock();
		IConfigurationElement secondConfigElementForAbc = new ConfigElementMock(ABC_SCHEME,
				secondAbcHandler);
		registerExtensions(schemeProcessor, configElementForAbc, secondConfigElementForAbc);

		schemeProcessor.handleUri(ABC_SCHEME, ABC_URI);

		assertTrue(abcHandler.called);
		assertFalse(secondAbcHandler.called);
