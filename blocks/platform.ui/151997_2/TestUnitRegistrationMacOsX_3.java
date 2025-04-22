	@Test
	public void returnsRegisteredSchemesPlus() throws Exception {
		fileProvider.readAnswers.put(OWN_APP_PLIST_PATH, getPlistFileReaderWithAdtSchemePlus());

		processStub.result = lsregisterDumpForOwnAppPlus;

		List<ISchemeInformation> infos = registration.getSchemesInformation(Arrays.asList(ADT_DEMO_SCHEME));

		assertEquals(1, infos.size());
		assertEquals("adt+demo", infos.get(0).getName());
		assertTrue(infos.get(0).isHandled());
	}

