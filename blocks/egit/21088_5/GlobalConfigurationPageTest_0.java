	}

	@Test
	public void testSubsectionWithDot() throws Exception {
		preferencePage.bot()
				.button(UIText.ConfigurationEditorComponent_AddButton).click();
		SWTBotShell addDialog = bot
				.shell(UIText.AddConfigEntryDialog_AddConfigTitle);
		addDialog.activate();

		String subsection = TESTSUBSECTION + "." + TESTNAME;
