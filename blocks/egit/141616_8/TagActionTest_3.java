	@Test
	public void testForceOverwriteLightWeigthTag() throws Exception {
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
	public void testMoveLightWeigthTag() throws Exception {
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
	public void testConvertLightWeigthIntoAnnotatedTag() throws Exception {
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
	public void testConvertAnnotatedTagIntoLightWeigth() throws Exception {
		Repository repo = lookupRepository(repositoryFile);
		Ref ref = repo.exactRef(Constants.R_TAGS + "SomeTag");
		ref = repo.getRefDatabase().peel(ref);
		assertTrue(ref.isPeeled());
		ObjectId commit = ref.getPeeledObjectId();

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
		ref = repo.exactRef(Constants.R_TAGS + "SomeTag");
		assertNotNull(ref);
		assertEquals(ref.getObjectId(), commit);
	}
