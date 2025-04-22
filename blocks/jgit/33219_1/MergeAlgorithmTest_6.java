	@Test
	public void testTwoSimilarModsAndOneInsertAtEnd() throws IOException {
		assertEquals(t("IAAJ")
		assertEquals(t("IAJ")
		assertEquals(t("IAAAJ")
	}

	@Test
	public void testTwoSimilarModsAndOneInsertAtEndNoNewlineAtEnd()
			throws IOException {
		newlineAtEnd = false;
		assertEquals(t("I<A=AAJ>")
		assertEquals(t("I<A=AJ>")
		assertEquals(t("I<A=AAAJ>")
	}

