	@Test
	public void testDeleteTag() throws Exception {
		SWTBotTree tree = getOrOpenView().bot().tree();
		int initialCount = myRepoViewUtil.getTagsItem(tree, repositoryFile)
				.expand().rowCount();

		createTag("Delete1", "The first tag");
		refreshAndWait();
		SWTBotTreeItem tagsItem = myRepoViewUtil.getTagsItem(tree,
				repositoryFile).expand();
		SWTBotTreeItem[] items = tagsItem.getItems();
		assertEquals("Wrong number of tags", initialCount + 1, items.length);
		tagsItem.getNode("Delete1").select().contextMenu("Delete Tag").click();
		bot.shell(UIText.DeleteTagCommand_titleConfirm).bot()
				.button(IDialogConstants.OK_LABEL).click();
		TestUtil.joinJobs(JobFamilies.TAG);
		refreshAndWait();
		tagsItem = myRepoViewUtil.getTagsItem(tree, repositoryFile).expand();
		items = tagsItem.getItems();
		assertEquals("Wrong number of tags", initialCount, items.length);
	}

	@Test
	public void testDeleteTags() throws Exception {
		SWTBotTree tree = getOrOpenView().bot().tree();
		int initialCount = myRepoViewUtil.getTagsItem(tree, repositoryFile)
				.expand().rowCount();

		createTag("Delete2", "The first tag");
		createTag("Delete3", "The second tag");
		refreshAndWait();
		SWTBotTreeItem tagsItem = myRepoViewUtil.getTagsItem(tree,
				repositoryFile).expand();
		SWTBotTreeItem[] items = tagsItem.getItems();
		assertEquals("Wrong number of tags", initialCount + 2, items.length);
		tagsItem.select("Delete2", "Delete3");
		ContextMenuHelper.clickContextMenu(tree, "Delete Tag");
		bot.shell(UIText.DeleteTagCommand_titleConfirm).bot()
				.button(IDialogConstants.OK_LABEL).click();
		TestUtil.joinJobs(JobFamilies.TAG);
		refreshAndWait();
		tagsItem = myRepoViewUtil.getTagsItem(tree, repositoryFile).expand();
		items = tagsItem.getItems();
		assertEquals("Wrong number of tags", initialCount, items.length);
	}

