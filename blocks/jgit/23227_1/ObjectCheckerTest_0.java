	@Test
	public void testValidPosixTree() throws CorruptObjectException {
		checkOneName("a<b>c:d|e");
		checkOneName("test ");
		checkOneName("test.");
	}

