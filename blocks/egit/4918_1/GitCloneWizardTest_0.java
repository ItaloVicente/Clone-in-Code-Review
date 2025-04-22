	
	@Test
	public void importFlatProjects() throws Exception {
		File repositoryFile = createProjectAndCommitToRepository();
		Activator.getDefault().getRepositoryUtil().addConfiguredRepository(
				repositoryFile);
		
		importWizard.openWizard();
		bot.tree().select(0);
		bot.button("Next >").click();
		bot.button("Next >").click();
		
		assertEquals(2, bot.tree().getAllItems().length);
	}

	@Test
	public void importSubProjects() throws Exception {
		File repositoryFile = createProjectAndCommitToRepository();
		Activator.getDefault().getRepositoryUtil().addConfiguredRepository(
				repositoryFile);
		createProjectsInSubfolder(repositoryFile);
		
		importWizard.openWizard();
		bot.tree().select(0);
		bot.button("Next >").click();
		bot.button("Next >").click();
		
		assertEquals(3, bot.tree().getAllItems().length);
		
		SWTBotTreeItem subFolder = bot.tree().expandNode("componentA");
		assertEquals(1, subFolder.getItems().length);
		
		SWTBotTreeItem swtBotTreeItem = subFolder.getItems()[0];
		swtBotTreeItem.check();
		
		bot.button("Finish").click();
		
		SWTBotTree projectExplorerTree = bot.viewById(
				"org.eclipse.jdt.ui.PackageExplorer").bot().tree();
		assertNotNull(getProjectItem(projectExplorerTree, "subprojectA1"));
		
	}
	private void createProjectsInSubfolder(File repositoryFile) throws Exception {
		Repository repository = new FileRepository(repositoryFile);
		
		Git git = new Git(repository);
		File subFolderA = new File(repository.getWorkTree(), "componentA");
		File projectA1Folder = new File(subFolderA, "projectA1");
		projectA1Folder.mkdirs();
		IProjectDescription descA1 = ResourcesPlugin.getWorkspace().newProjectDescription("subprojectA1");
		descA1.setLocation(new Path(new File(new File(repository.getWorkTree(), "componentA"),"subprojectA1")
				.getPath()));
		new ModelObjectWriter().write(descA1, new FileOutputStream(new File(projectA1Folder, ".project")));
		git.add().addFilepattern("componentA").call();
		git.commit().setMessage("added subproject").call();
	}
