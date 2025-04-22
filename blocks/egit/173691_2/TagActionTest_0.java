	@Test
	public void testForceOverwriteLightWeightTagWithTextInput()
			throws Exception {
		assertIsLightweight("SomeLightTag", someLightTagCommit);
		SWTBotShell tagDialog = openTagDialog();
		tagDialog.bot().textWithLabel(UIText.CreateTagDialog_tagName)
				.setText("SomeLightTag");
		assertFalse("Ok should be disabled", tagDialog.bot()
				.button(UIText.CreateTagDialog_CreateTagButton).isEnabled());
		assertTrue("Overwrite checkbox should be enabled", tagDialog.bot()
				.checkBox(UIText.CreateTagDialog_overwriteTag).isEnabled());
		tagDialog.bot().checkBox(UIText.CreateTagDialog_overwriteTag).click();
		tagDialog.bot().button(UIText.CreateTagDialog_CreateTagButton).click();
		TestUtil.joinJobs(JobFamilies.TAG);
		assertIsLightweight("SomeLightTag", headCommit);
	}

