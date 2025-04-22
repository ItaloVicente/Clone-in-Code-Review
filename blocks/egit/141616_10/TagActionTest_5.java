	@Test
	public void testForceOverwriteLightWeightTag() throws Exception {
		assertIsLightweight("SomeLightTag", someLightTagCommit);
		SWTBotShell tagDialog = openTagDialog();
		tagDialog.bot().tableWithLabel(UIText.CreateTagDialog_existingTags)
				.getTableItem("SomeLightTag").select();
		assertFalse("Ok should be disabled", tagDialog.bot()
				.button(UIText.CreateTagDialog_CreateTagButton).isEnabled());
		tagDialog.bot().checkBox(UIText.CreateTagDialog_overwriteTag).click();
		tagDialog.bot().button(UIText.CreateTagDialog_CreateTagButton).click();
		TestUtil.joinJobs(JobFamilies.TAG);
		assertIsLightweight("SomeLightTag", headCommit);
	}

	@Test
	public void testConvertLightWeightIntoAnnotatedTag() throws Exception {
		assertIsLightweight("SomeLightTag", someLightTagCommit);

		SWTBotShell tagDialog = openTagDialog();
		tagDialog.bot().tableWithLabel(UIText.CreateTagDialog_existingTags)
				.getTableItem("SomeLightTag").select();
		assertFalse("Ok should be disabled", tagDialog.bot()
				.button(UIText.CreateTagDialog_CreateTagButton).isEnabled());
		tagDialog.bot().styledTextWithLabel(UIText.CreateTagDialog_tagMessage)
				.setText("New message");
		tagDialog.bot().checkBox(UIText.CreateTagDialog_overwriteTag).click();
		tagDialog.bot().button(UIText.CreateTagDialog_CreateTagButton).click();
		TestUtil.joinJobs(JobFamilies.TAG);
		assertIsAnnotated("SomeLightTag", headCommit, "New message");
	}

	@Test
	public void testConvertAnnotatedTagIntoLightWeight() throws Exception {
		assertIsAnnotated("SomeTag", someTagCommit, null);

		SWTBotShell tagDialog = openTagDialog();
		tagDialog.bot().tableWithLabel(UIText.CreateTagDialog_existingTags)
				.getTableItem("SomeTag").select();
		assertFalse("Ok should be disabled", tagDialog.bot()
				.button(UIText.CreateTagDialog_CreateTagButton).isEnabled());
		tagDialog.bot().checkBox(UIText.CreateTagDialog_overwriteTag).click();
		tagDialog.bot().styledTextWithLabel(UIText.CreateTagDialog_tagMessage)
				.setText("");
		tagDialog.bot().button(UIText.CreateTagDialog_CreateTagButton).click();
		TestUtil.joinJobs(JobFamilies.TAG);
		assertIsLightweight("SomeTag", headCommit);
	}
