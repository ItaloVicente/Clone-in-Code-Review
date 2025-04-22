	static private void assertStringArrayEquals(String expected, String[] actual) {
		assertEquals(
				1,
				actual.length > 1 && actual[actual.length - 1].equals("") ? actual.length - 1
						: actual.length);
		assertEquals(expected, actual[0]);
	}

