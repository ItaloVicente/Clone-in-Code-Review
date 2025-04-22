
	@Test
	public void testBranchConfigurationDecoration() throws Exception {
		SWTBotView view = getOrOpenView();

		SWTBotTreeItem repoItem = myRepoViewUtil.getRootItem(view.bot().tree(),
				clonedRepositoryFile);
		TestUtil.waitForDecorations();
		String label = repoItem.getText();
		assertTrue("Expected branch decoration to be present: " + label,
				label.contains("[master]"));
		SWTBotTreeItem localItem = myRepoViewUtil
				.getLocalBranchesItem(view.bot().tree(), clonedRepositoryFile);
		TestUtil.expandAndWait(localItem).getNode("master").select();
		ContextMenuHelper.clickContextMenu(view.bot().tree(),
				myUtil.getPluginLocalizedValue("ConfigurBranchCommand.label"));

		SWTBotShell configureBranchDialog = bot.shell(
				UIText.BranchConfigurationDialog_BranchConfigurationTitle);
		configureBranchDialog.bot()
				.comboBoxWithLabel(
						UIText.BranchConfigurationDialog_UpstreamBranchLabel)
				.setSelection(0);
		final AtomicBoolean changed = new AtomicBoolean();
		ConfigChangedListener listener = new ConfigChangedListener() {
			@Override
			public void onConfigChanged(ConfigChangedEvent event) {
				changed.set(true);
			}
		};
		ListenerHandle handle = lookupRepository(clonedRepositoryFile)
				.getConfig().addChangeListener(listener);
		configureBranchDialog.bot()
				.button(UIText.BranchConfigurationDialog_ButtonOK).click();
		bot.waitUntil(Conditions.shellCloses(configureBranchDialog));
		handle.remove();
		assertTrue("Expected a ConfigChangeEvent", changed.get());
		TestUtil.waitForJobs(100, 5000);
		TestUtil.waitForDecorations();
		repoItem = myRepoViewUtil.getRootItem(view.bot().tree(),
				clonedRepositoryFile);
		label = repoItem.getText();
		assertTrue("Expected branch decoration to be updated: " + label,
				label.contains("[master \u21911]"));
	}
