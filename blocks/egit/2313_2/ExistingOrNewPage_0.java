		assertContents(rows);
		assertEquals(newRepoPath, bot.text().getText());
	}

	@SuppressWarnings("boxing")
	private void assertContents(Row[] rows) {
