	@Test
	public void testCreateLightWeightTag() throws Exception {
		SWTBotShell tagDialog = openTagDialog();
		tagDialog.bot().textWithLabel(UIText.CreateTagDialog_tagName)
				.setText("AnotherLightTag");
		tagDialog.bot().button(UIText.CreateTagDialog_CreateTagButton).click();
		waitInUI();
		assertNotNull(lookupRepository(repositoryFile)
				.exactRef(Constants.R_TAGS + "AnotherLightTag"));
	}

