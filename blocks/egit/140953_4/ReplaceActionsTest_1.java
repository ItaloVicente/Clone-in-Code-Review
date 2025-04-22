		assertThat(getTestFileContent(), not(initialContent));
		String menuLabel = util.getPluginLocalizedValue(
				"ReplaceWithPreviousVersionAction.label");
		JobJoiner jobJoiner = JobJoiner.startListening(
				JobFamilies.DISCARD_CHANGES, 30, TimeUnit.SECONDS);
		clickReplaceWith(menuLabel);
		jobJoiner.join();
		assertEquals(initialContent, getTestFileContent());
	}

	@Test
	public void testReplaceWithPreviousChanged() throws Exception {
