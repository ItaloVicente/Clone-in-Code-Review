
		assertParseCommandFails(o + " " + n + " " + r + "\n");
		assertParseCommandFails(o + " " + n + " " + "refs^foo");
		assertParseCommandFails(o + " " + n.substring(10) + " " + r);
		assertParseCommandFails(o.substring(10) + " " + n + " " + r);
		assertParseCommandFails("X" + o.substring(1) + " " + n + " " + r);
		assertParseCommandFails(o + " " + "X" + n.substring(1) + " " + r);
	}

	private void assertParseCommandFails(String input) {
		try {
			BaseReceivePack.parseCommand(input);
			fail();
		} catch (PackProtocolException e) {
		}
