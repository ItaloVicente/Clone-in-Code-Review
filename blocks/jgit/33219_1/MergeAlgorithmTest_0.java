	@Test
	public void testTwoConflictingModificationsNoNewlineAtEnd()
			throws IOException {
		newlineAtEnd = false;
		testTwoConflictingModifications();
	}

