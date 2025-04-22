	@Test
	public void dontWrapWordLongerThanOneLineAndKeepSpaceAtFront() {
		String input = " 1234567890123456789012345678901234567890123456789012345678901234567890123456789012";
		assertWrappedEquals(input, input);
	}

