	@Test
	public void testSeparateModificationsNoNewlineAtEnd() throws IOException {
		newlineAtEnd = false;
		testSeparateModifications();
	}

	@Test
	public void testBlankLines() throws IOException {
		assertEquals(t("aZc\nYe")
	}

	@Test
	public void testBlankLinesNoNewlineAtEnd() throws IOException {
		newlineAtEnd = false;
		testBlankLines();
	}

