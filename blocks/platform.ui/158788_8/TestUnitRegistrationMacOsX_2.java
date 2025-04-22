	@Test
	public void returnsRegisteredSchemesOnMacOS_10_15_3() throws Exception {
		fileProvider.readAnswers.put(OWN_APP_PLIST_PATH, getPlistFileReaderWithAdtScheme());

		processStub.result = lsregisterDumpMacOS10_15_3;

		List<ISchemeInformation> infos = registration.getSchemesInformation(Arrays.asList(ADT_SCHEME));

		assertEquals(1, infos.size());
		assertEquals("adt", infos.get(0).getName());
		assertTrue(infos.get(0).isHandled());
	}

	@Test
	public void returnsRegisteredSchemesOnMacOS10_15_3() throws Exception {
		fileProvider.readAnswers.put(OWN_APP_PLIST_PATH, getPlistFileReaderWithAdtScheme());

		processStub.result = lsregisterDumpForOwnApp;

		List<ISchemeInformation> infos = registration.getSchemesInformation(Arrays.asList(ADT_SCHEME));

		assertEquals(1, infos.size());
		assertEquals("adt", infos.get(0).getName());
		assertTrue(infos.get(0).isHandled());
	}

