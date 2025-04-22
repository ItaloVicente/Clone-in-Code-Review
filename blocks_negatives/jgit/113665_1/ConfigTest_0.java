	@Test
	public void testQuotingForSubSectionNames() {
		String resultPattern = "[testsection \"{0}\"]\n\ttestname = testvalue\n";
		String result;

		Config config = new Config();
		config.setString("testsection", "testsubsection", "testname",
				"testvalue");

		result = MessageFormat.format(resultPattern, "testsubsection");
		assertEquals(result, config.toText());
		config.clear();

		config.setString("testsection", "#quotable", "testname", "testvalue");
		result = MessageFormat.format(resultPattern, "#quotable");
		assertEquals(result, config.toText());
		config.clear();

		config.setString("testsection", "with\"quote", "testname", "testvalue");
		result = MessageFormat.format(resultPattern, "with\\\"quote");
		assertEquals(result, config.toText());
	}
