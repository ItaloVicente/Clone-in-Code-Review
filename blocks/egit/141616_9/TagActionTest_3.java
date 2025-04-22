	@Test
	public void testForceOverwriteLightWeightTag() throws Exception {
		SWTBotShell tagDialog = openTagDialog();
		tagDialog.bot().tableWithLabel(UIText.CreateTagDialog_existingTags)
				.getTableItem("SomeLightTag").select();
		assertFalse("Ok should be disabled", tagDialog.bot()
				.button(UIText.CreateTagDialog_CreateTagButton).isEnabled());
		tagDialog.bot().checkBox(UIText.CreateTagDialog_overwriteTag).click();
		tagDialog.bot().button(UIText.CreateTagDialog_CreateTagButton).click();
		waitInUI();
		assertNotNull(lookupRepository(repositoryFile)
				.exactRef(Constants.R_TAGS + "SomeLightTag"));
	}

	@Test
	public void testMoveLightWeightTag() throws Exception {
		Repository repo = lookupRepository(repositoryFile);
		touchAndSubmit(null);
		ObjectId currId = repo.resolve(repo.getFullBranch());

		SWTBotShell tagDialog = openTagDialog();
		tagDialog.bot().tableWithLabel(UIText.CreateTagDialog_existingTags)
				.getTableItem("SomeLightTag").select();
		assertFalse("Ok should be disabled", tagDialog.bot()
				.button(UIText.CreateTagDialog_CreateTagButton).isEnabled());
		tagDialog.bot().checkBox(UIText.CreateTagDialog_overwriteTag).click();
		tagDialog.bot().button(UIText.CreateTagDialog_CreateTagButton).click();
		waitInUI();
		Ref ref = repo.exactRef(Constants.R_TAGS + "SomeLightTag");
		assertNotNull(ref);
		assertEquals(ref.getObjectId(), currId);
	}

	@Test
	public void testConvertLightWeightIntoAnnotatedTag() throws Exception {
		Repository repo = lookupRepository(repositoryFile);
		ObjectId currId = repo.resolve(repo.getFullBranch());

		SWTBotShell tagDialog = openTagDialog();
		tagDialog.bot().tableWithLabel(UIText.CreateTagDialog_existingTags)
				.getTableItem("SomeLightTag").select();
		assertFalse("Ok should be disabled", tagDialog.bot()
				.button(UIText.CreateTagDialog_CreateTagButton).isEnabled());
		tagDialog.bot().styledTextWithLabel(UIText.CreateTagDialog_tagMessage)
				.setText("New message");
		tagDialog.bot().checkBox(UIText.CreateTagDialog_overwriteTag).click();
		tagDialog.bot().button(UIText.CreateTagDialog_CreateTagButton).click();
		waitInUI();
		Ref ref = repo.exactRef(Constants.R_TAGS + "SomeLightTag");
		assertNotNull(ref);
		assertNotEquals(ref.getObjectId(), currId);
	}

	@Test
	public void testConvertAnnotatedTagIntoLightWeight() throws Exception {
		Repository repo = lookupRepository(repositoryFile);
		ObjectId currId = repo.exactRef(Constants.HEAD).getObjectId();

		SWTBotShell tagDialog = openTagDialog();
		tagDialog.bot().tableWithLabel(UIText.CreateTagDialog_existingTags)
				.getTableItem("SomeTag").select();
		assertFalse("Ok should be disabled", tagDialog.bot()
				.button(UIText.CreateTagDialog_CreateTagButton).isEnabled());
		tagDialog.bot().checkBox(UIText.CreateTagDialog_overwriteTag).click();
		tagDialog.bot().styledTextWithLabel(UIText.CreateTagDialog_tagMessage)
				.setText("");
		tagDialog.bot().button(UIText.CreateTagDialog_CreateTagButton).click();
		waitInUI();
		Ref ref = repo.exactRef(Constants.R_TAGS + "SomeTag");
		assertNotNull(ref);
		assertEquals(ref.getObjectId(), currId);
	}
