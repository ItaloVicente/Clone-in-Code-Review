	@Test
	public void testCreateRepository() throws Exception {
		clearView();
		refreshAndWait();
		assertEmpty();
		getOrOpenView()
				.toolbarButton(
						myUtil
								.getPluginLocalizedValue("RepoViewCreateRepository.tooltip"))
				.click();
		SWTBotShell shell = bot.shell(UIText.NewRepositoryWizard_WizardTitle)
				.activate();
		IPath newPath = new Path(testDirectory.getPath())
				.append("NewRepository");
		shell.bot().textWithLabel(UIText.CreateRepositoryPage_DirectoryLabel)
				.setText(newPath.toOSString());
		shell.bot().button(IDialogConstants.FINISH_LABEL).click();
		refreshAndWait();
		File repoFile = new File(newPath.toFile(), Constants.DOT_GIT);
		myRepoViewUtil.getRootItem(getOrOpenView().bot().tree(), repoFile);
		assertFalse(myRepoViewUtil.lookupRepository(repoFile).isBare());

		getOrOpenView()
				.toolbarButton(
						myUtil
								.getPluginLocalizedValue("RepoViewCreateRepository.tooltip"))
				.click();
		shell = bot.shell(UIText.NewRepositoryWizard_WizardTitle).activate();
		newPath = new Path(testDirectory.getPath()).append("bare").append(
				"NewBareRepository");
		shell.bot().textWithLabel(UIText.CreateRepositoryPage_DirectoryLabel)
				.setText(newPath.toOSString());
		shell.bot().checkBox(UIText.CreateRepositoryPage_BareCheckbox).select();
		shell.bot().button(IDialogConstants.FINISH_LABEL).click();
		refreshAndWait();
		repoFile = newPath.toFile();
		myRepoViewUtil.getRootItem(getOrOpenView().bot().tree(), repoFile);
		assertTrue(myRepoViewUtil.lookupRepository(repoFile).isBare());
	}

