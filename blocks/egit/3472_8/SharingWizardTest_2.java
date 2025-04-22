
	@Test
	public void shareProjectWithExternalRepo() throws Exception {
		String repoName = "ExternalRepositoryForShare";
		createProject(projectName0);
		String location1 = createProject(projectName1);
		String location2 = createProject(projectName2);
		createProject(projectName3);

		ExistingOrNewPage existingOrNewPage = sharingWizard.openWizard(
				projectName1, projectName2);
		SWTBotShell createRepoDialog = existingOrNewPage
				.clickCreateRepository();
		String repoDir = Activator.getDefault().getPreferenceStore()
				.getString(UIPreferences.DEFAULT_REPO_DIR);
		File repoFolder = new File(repoDir, repoName);
		createRepoDialog.bot()
				.textWithLabel(UIText.CreateRepositoryPage_DirectoryLabel)
				.setText(repoFolder.getPath());
		createRepoDialog.bot().button(IDialogConstants.FINISH_LABEL).click();

		SWTBotCombo combo = bot
				.comboBoxWithLabel(UIText.ExistingOrNewPage_ExistingRepositoryLabel);
		assertTrue(combo.getText().startsWith(repoName));
		Repository targetRepo = lookupRepository(new File(repoFolder,
				Constants.DOT_GIT));

		assertTrue(combo.getText()
				.endsWith(targetRepo.getDirectory().getPath()));
		assertEquals(
				targetRepo.getWorkTree().getPath(),
				bot.textWithLabel(
						UIText.ExistingOrNewPage_WorkingDirectoryLabel)
						.getText());
		String[][] contents = new String[2][3];
		contents[0][0] = projectName1;
		contents[0][1] = new Path(location1).toString();
		contents[0][2] = new Path(targetRepo.getWorkTree().getPath()).append(
				projectName1).toString();

		contents[1][0] = projectName2;
		contents[1][1] = new Path(location2).toString();
		contents[1][2] = new Path(targetRepo.getWorkTree().getPath()).append(
				projectName2).toString();
		existingOrNewPage.assertTableContents(contents);

		existingOrNewPage.setRelativePath("a/b");

		contents[0][2] = new Path(targetRepo.getWorkTree().getPath())
				.append("a/b").append(projectName1).toString();
		contents[1][2] = new Path(targetRepo.getWorkTree().getPath())
				.append("a/b").append(projectName2).toString();
		existingOrNewPage.assertTableContents(contents);

		bot.button(IDialogConstants.FINISH_LABEL).click();
		Thread.sleep(1000);
		String location1Path = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(projectName1).getLocation().toString();
		assertEquals(contents[0][2], location1Path);
		String location2Path = ResourcesPlugin.getWorkspace().getRoot()
				.getProject(projectName2).getLocation().toString();
		assertEquals(contents[1][2], location2Path);
	}
