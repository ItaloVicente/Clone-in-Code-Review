	}

	@Test
	public void testSubsectionWithDot() throws Exception {
		preferencePage.bot()
				.button(UIText.ConfigurationEditorComponent_AddButton).click();
		SWTBotShell addDialog = bot
				.shell(UIText.AddConfigEntryDialog_AddConfigTitle);
		addDialog.activate();

		addDialog
				.bot()
				.textWithLabel(UIText.AddConfigEntryDialog_KeyLabel)
