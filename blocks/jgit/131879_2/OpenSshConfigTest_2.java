	@Test
	public void testCaseInsensitiveKeyLookup() throws Exception {
		config("Host orcz\n" + "Port 29418\n"
				+ "\tHostName repo.or.cz\nStrictHostKeyChecking yes\n");
		final Host h = osc.lookup("orcz");
		Config c = h.getConfig();
		String exactCase = c.getValue("StrictHostKeyChecking");
		assertEquals("yes"
		assertEquals(exactCase
		assertEquals(exactCase
		assertEquals(exactCase
		assertNull(c.getValue("sTrIcThostKEYcheckIN"));
	}

