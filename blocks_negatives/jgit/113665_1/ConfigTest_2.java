		assertEquals("x", Config.escapeValue("x"));
		assertEquals("\"x  \"", Config.escapeValue("x  "));
		assertEquals("x\" \"", Config.escapeValue("x "));

		assertParseRoundTrip("x");
		assertParseRoundTrip("x ");
		assertParseRoundTrip("x  ");
