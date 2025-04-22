		assertStringMatchesPatternWithInexactNumber(lines.get(0),
				"some-domain1\tTRUE\t/some/path1\tFALSE\t(\\d*)\tkey1\tvalueFromSimple2",
				JAN_01_2030_NOON, 1000);
		assertStringMatchesPatternWithInexactNumber(lines.get(1),
				"some-domain1\tTRUE\t/some/path1\tFALSE\t(\\d*)\tkey3\tvalueFromSimple2",
				JAN_01_2030_NOON, 1000);
		assertStringMatchesPatternWithInexactNumber(lines.get(2),
				"some-domain1\tTRUE\t/some/path1\tFALSE\t(\\d*)\tkey2\tvalueFromSimple1",
				JAN_01_2030_NOON, 1000);
	}

	@SuppressWarnings("boxing")
	private static final void assertStringMatchesPatternWithInexactNumber(
			String string, String pattern, long expectedNumericValue,
			long delta) {
		java.util.regex.Matcher matcher = Pattern.compile(pattern)
				.matcher(string);
		assertTrue("Given string '" + string + "' does not match '" + pattern
				+ "'", matcher.matches());
		Long actualNumericValue = Long.decode(matcher.group(1));

		assertTrue(
				"Value is supposed to be close to " + expectedNumericValue
						+ " but is " + actualNumericValue + ".",
				Math.abs(expectedNumericValue - actualNumericValue) <= delta);
