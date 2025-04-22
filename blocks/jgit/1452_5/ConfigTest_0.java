	public void testQuotingForSubSectionNames() {
		String resultPattern = "[testsection \"{0}\"]\n\ttestname = testvalue\n";
		String result;

		Config config = new Config();
		config.setString("testsection"
				"testvalue");

		result = MessageFormat.format(resultPattern
		assertEquals(result
		config.clear();

		config.setString("testsection"
		result = MessageFormat.format(resultPattern
		assertEquals(result
		config.clear();

		config.setString("testsection"
		result = MessageFormat.format(resultPattern
		assertEquals(result
	}

