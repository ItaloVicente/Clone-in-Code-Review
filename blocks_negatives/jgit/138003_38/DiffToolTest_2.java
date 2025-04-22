
	private static void assertArrayOfLinesEquals(String failMessage,
			String[] expected, String[] actual) {
		assertEquals(failMessage, toString(expected), toString(actual));
	}

	private static String getEchoCommand() {
		/*
		 * use 'MERGED' placeholder, as both 'LOCAL' and 'REMOTE' will be
		 * replaced with full paths to a temporary file during some of the tests
		 */
		return "(echo \"$MERGED\")";
	}
