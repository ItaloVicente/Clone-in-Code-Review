
	private void assertArrayOfLinesEquals(String[] expected, String[] actual) {
		assertEquals(toText(expected), toText(actual));
	}

	private String toText(String[] lines) {
		StringBuilder b = new StringBuilder();
		for (String s : lines) {
			b.append(s);
			b.append('\n');
		}
		return b.toString();
	}
