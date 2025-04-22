
	@Test
	public void testBranchConfiguration() throws Exception {
		Repository repo = lookupRepository(clonedRepositoryFile);
		Git git = new Git(repo);
		git.branchCreate().setName("configTest")
				.setStartPoint("refs/remotes/origin/master")
				.setUpstreamMode(SetupUpstreamMode.TRACK).call();

		boolean rebase = repo.getConfig().getBoolean(
				ConfigConstants.CONFIG_BRANCH_SECTION, "configTest",
				ConfigConstants.CONFIG_KEY_REBASE, false);
		assertFalse(rebase);

		Activator.getDefault().getRepositoryUtil()
				.addConfiguredRepository(clonedRepositoryFile);

		SWTBotView view = getOrOpenView();

		SWTBotTreeItem localItem = myRepoViewUtil.getLocalBranchesItem(view
				.bot().tree(), clonedRepositoryFile);
		localItem.expand().getNode("configTest").select();

		ContextMenuHelper.clickContextMenu(view.bot().tree(),
				myUtil.getPluginLocalizedValue("ShowIn"),
				myUtil.getPluginLocalizedValue("RepoViewOpenProperties.label"));

		SWTBotView propsView = bot.viewByTitle("Properties");
		SWTBotTreeItem rootItem = propsView
				.bot()
				.tree()
				.getTreeItem(
						UIText.BranchPropertySource_UpstreamConfigurationCategory);
		SWTBotTreeItem rebaseItem = rootItem.expand().getNode(
				UIText.BranchPropertySource_RebaseDescriptor);
		assertEquals(UIText.BranchPropertySource_ValueNotSet,
				rebaseItem.cell(1));

		SWTBotTreeItem remoteItem = rootItem.expand().getNode(
				UIText.BranchPropertySource_RemoteDescriptor);
		assertEquals("origin", remoteItem.cell(1));

		SWTBotTreeItem upstreamItem = rootItem.expand().getNode(
				UIText.BranchPropertySource_UpstreamBranchDescriptor);
		assertEquals("refs/heads/master", upstreamItem.cell(1));

		view = getOrOpenView();

		localItem = myRepoViewUtil.getLocalBranchesItem(view.bot().tree(),
				clonedRepositoryFile);
		localItem.expand().getNode("configTest").select();

		ContextMenuHelper.clickContextMenu(view.bot().tree(),
				myUtil.getPluginLocalizedValue("ConfigurBranchCommand.label"));

		SWTBotShell configureBranchDialog = bot
				.shell(UIText.BranchConfigurationDialog_BranchConfigurationTitle);
		assertEquals(MessageFormat.format(
				UIText.BranchConfigurationDialog_EditBranchConfigMessage,
				"configTest"), configureBranchDialog.bot().text().getText());
		assertEquals(
				"refs/heads/master",
				configureBranchDialog
						.bot()
						.comboBoxWithLabel(
								UIText.BranchConfigurationDialog_UpstreamBranchLabel)
						.getText());
		assertEquals(
				"origin",
				configureBranchDialog
						.bot()
						.comboBoxWithLabel(
								UIText.BranchConfigurationDialog_RemoteLabel)
						.getText());
		assertFalse(configureBranchDialog.bot()
				.checkBox(UIText.BranchConfigurationDialog_RebaseLabel)
				.isChecked());

		configureBranchDialog.bot()
				.checkBox(UIText.BranchConfigurationDialog_RebaseLabel)
				.select();
		final AtomicBoolean changed = new AtomicBoolean();
		ConfigChangedListener listener =
		new ConfigChangedListener() {
			public void onConfigChanged(ConfigChangedEvent event) {
				changed.set(true);
			}
		};
		ListenerHandle handle = repo.getConfig().addChangeListener(listener);
		configureBranchDialog.bot().button(Window.OK).click();
		for (int i = 0; i<10;i++){
			waitInUI();
			if (changed.get())
				break;
		}
		handle.remove();
		if (!changed.get())
			fail("We should have gotten a config change event");

		rebase = repo.getConfig().getBoolean(
				ConfigConstants.CONFIG_BRANCH_SECTION, "configTest",
				ConfigConstants.CONFIG_KEY_REBASE, false);
		assertTrue(rebase);

		localItem = myRepoViewUtil.getLocalBranchesItem(view.bot().tree(),
				clonedRepositoryFile);
		localItem.expand().getNode("configTest").select();

		ContextMenuHelper.clickContextMenu(view.bot().tree(),
				myUtil.getPluginLocalizedValue("ShowIn"),
				myUtil.getPluginLocalizedValue("RepoViewOpenProperties.label"));

		propsView = bot.viewByTitle("Properties");
		rootItem = propsView
				.bot()
				.tree()
				.getTreeItem(
						UIText.BranchPropertySource_UpstreamConfigurationCategory);
		rebaseItem = rootItem.expand().getNode(
				UIText.BranchPropertySource_RebaseDescriptor);
		assertEquals("true", rebaseItem.cell(1));
	}
