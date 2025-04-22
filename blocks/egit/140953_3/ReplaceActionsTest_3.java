		assertEquals(initialContent, getTestFileContent());
	}

	@Test
	public void testReplaceWithPreviousChangedClosed() throws Exception {
		String initialContent = getTestFileContent();
		touchAndSubmit(null);
		String newContent = getTestFileContent();
		assertThat(newContent, not(initialContent));
		touch("Something else");
		String changedContent = getTestFileContent();
		assertThat(changedContent, not(initialContent));
		assertThat(changedContent, not(newContent));
		String menuLabel = util.getPluginLocalizedValue(
				"ReplaceWithPreviousVersionAction.label");
		clickReplaceWith(menuLabel);
		SWTBotShell confirm = bot
				.shell(UIText.DiscardChangesAction_confirmActionTitle);
		confirm.close();
		TestUtil.processUIEvents();
		assertEquals(changedContent, getTestFileContent());
