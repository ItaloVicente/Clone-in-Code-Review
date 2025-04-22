	protected static void assertArrayOfMatchingLines(String failMessage
			Pattern[] expected
		assertEquals(failMessage + System.lineSeparator()
				+ "Expected and actual lines count don't match. Expected: "
				+ Arrays.asList(expected) + "
				+ Arrays.asList(actual)
		int n = expected.length;
		for (int i = 0; i < n; ++i) {
			Pattern expectedPattern = expected[i];
			String actualLine = actual[i];
			Matcher matcher = expectedPattern.matcher(actualLine);
			boolean matches = matcher.matches();
			assertTrue(failMessage + System.lineSeparator() + "Line " + i + " '"
					+ actualLine + "' doesn't match expected pattern: "
					+ expectedPattern + System.lineSeparator() + "Expected: "
					+ Arrays.asList(expected) + "
					+ Arrays.asList(actual)
					matches);
		}
