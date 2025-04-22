		TestUtil.joinJobs(JobFamilies.TAG);
		assertIsAnnotated("AnotherTag", headCommit, "Here's the message text");
	}

	@Test
	public void testCreateLightWeightTag() throws Exception {
		SWTBotShell tagDialog = openTagDialog();
		tagDialog.bot().textWithLabel(UIText.CreateTagDialog_tagName)
				.setText("AnotherLightTag");
		tagDialog.bot().button(UIText.CreateTagDialog_CreateTagButton).click();
		TestUtil.joinJobs(JobFamilies.TAG);
		assertIsLightweight("AnotherLightTag", headCommit);
