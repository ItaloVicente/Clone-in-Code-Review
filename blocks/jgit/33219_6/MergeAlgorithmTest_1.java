	@Test
	public void testTwoSimilarModsAndOneInsertAtEnd() throws IOException {
		Assume.assumeTrue(newlineAtEnd);
		assertEquals(t("IAAJ")
		assertEquals(t("IAJ")
		assertEquals(t("IAAAJ")
	}

	@Test
	public void testTwoSimilarModsAndOneInsertAtEndNoNewlineAtEnd()
			throws IOException {
		Assume.assumeFalse(newlineAtEnd);
		assertEquals(t("I<A=AAJ>")
		assertEquals(t("I<A=AJ>")
		assertEquals(t("I<A=AAAJ>")
