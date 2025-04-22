	@Test
	public void testStashDeleteCreate() throws Exception {
		Repository repo = lookupRepository(repositoryFile);
		IFile file = touch("Something");
		new StashCreateOperation(repo, "First stash").execute(null);
		file.refreshLocal(IResource.DEPTH_ZERO, null);
		touch("Something else");
		new StashCreateOperation(repo, "Second stash").execute(null);
		file.refreshLocal(IResource.DEPTH_ZERO, null);
		touch("Something else again");
		new StashCreateOperation(repo, "Third stash").execute(null);
		file.refreshLocal(IResource.DEPTH_ZERO, null);
		SWTBotTree tree = getOrOpenView().bot().tree();
		refreshAndWait();

		SWTBotTreeItem item = myRepoViewUtil.getStashesItem(tree,
				repositoryFile);
		item = TestUtil.expandAndWait(item);
		assertEquals("Unexpected number of stashed commits", 3,
				item.getItems().length);
		item.getNode(0).select();
		ContextMenuHelper.clickContextMenu(tree,
				myUtil.getPluginLocalizedValue("StashDropCommand.label"));
		SWTBotShell confirm = bot.shell(UIText.StashDropCommand_confirmTitle);
		confirm.bot().button(UIText.StashDropCommand_buttonDelete).click();
		bot.waitUntil(shellCloses(confirm));
		TestUtil.joinJobs(JobFamilies.STASH);
		TestUtil.joinJobs(JobFamilies.REPO_VIEW_REFRESH);
		touch("Something different");
		tree.getAllItems()[0].select();
		ContextMenuHelper.clickContextMenu(tree,
				myUtil.getPluginLocalizedValue("StashesMenu.label"),
				UIText.StashesMenu_StashChangesActionText);
		confirm = bot.shell(UIText.StashCreateCommand_titleEnterCommitMessage);
		confirm.bot().button(UIText.StashCreateCommand_ButtonOK).click();
		bot.waitUntil(shellCloses(confirm));
		TestUtil.joinJobs(JobFamilies.STASH);
		TestUtil.joinJobs(JobFamilies.REPO_VIEW_REFRESH);
		item = myRepoViewUtil.getStashesItem(tree, repositoryFile);
		assertStashes(item.getItems(), 3);
	}

	@Test
	public void testStashDeleteMultiple() throws Exception {
		Repository repo = lookupRepository(repositoryFile);
		IFile file = touch("Something");
		new StashCreateOperation(repo, "First stash").execute(null);
		file.refreshLocal(IResource.DEPTH_ZERO, null);
		touch("Something else");
		new StashCreateOperation(repo, "Second stash").execute(null);
		file.refreshLocal(IResource.DEPTH_ZERO, null);
		touch("Something else again");
		new StashCreateOperation(repo, "Third stash").execute(null);
		file.refreshLocal(IResource.DEPTH_ZERO, null);
		SWTBotTree tree = getOrOpenView().bot().tree();
		refreshAndWait();

		SWTBotTreeItem item = myRepoViewUtil.getStashesItem(tree,
				repositoryFile);
		item = TestUtil.expandAndWait(item);
		assertEquals("Unexpected number of stashed commits", 3,
				item.getItems().length);
		item.select(1, 2);
		ContextMenuHelper.clickContextMenu(tree,
				myUtil.getPluginLocalizedValue("StashDropCommand.label"));
		SWTBotShell confirm = bot.shell(UIText.StashDropCommand_confirmTitle);
		confirm.bot().button(UIText.StashDropCommand_buttonDelete).click();
		bot.waitUntil(shellCloses(confirm));
		TestUtil.joinJobs(JobFamilies.STASH);
		TestUtil.joinJobs(JobFamilies.REPO_VIEW_REFRESH);
		item = myRepoViewUtil.getStashesItem(tree, repositoryFile);
		assertStashes(item.getItems(), 1, "Third stash");
	}

	private void assertStashes(SWTBotTreeItem[] children, int expectedSize,
			String... decorations)
			throws Exception {
		assertEquals("Expected " + expectedSize + " children", expectedSize,
				children.length);
		if (decorations != null && decorations.length > 0) {
			TestUtil.waitForDecorations();
		}
		int[] indices = new int[expectedSize];
		int[] expectedIndices = new int[expectedSize];
		for (int i = 0; i < indices.length; i++) {
			indices[i] = -1;
			expectedIndices[i] = i;
		}
		children[0].display.syncExec(() -> {
			int j = 0;
			for (SWTBotTreeItem child : children) {
				Object data = child.widget.getData();
				if (data instanceof StashedCommitNode) {
					indices[j++] = ((StashedCommitNode) data).getIndex();
				}
			}
		});
		assertArrayEquals("Unexpected stash indices", expectedIndices, indices);
		for (int i = 0; i < children.length; i++) {
			String text = children[i].getText();
			assertTrue("Stash " + i + " has wrong label: " + text,
					text.startsWith("stash@{" + i + "}"));
			if (decorations != null && i < decorations.length) {
				String deco = decorations[i];
				if (deco != null) {
					assertTrue("Label should contain '" + deco + "': " + text,
							text.contains(deco));
				}
			}
		}
	}

