	@Test
	public void testImportWizardGeneralProjectWithWorkingSet() throws Exception {
		deleteAllProjects();
		assertProjectExistence(PROJ1, false);
		SWTBotTree tree = getOrOpenView().bot().tree();
		String wizardTitle = NLS.bind(
				UIText.GitCreateProjectViaWizardWizard_WizardTitle,
				repositoryFile.getPath());
		getWorkdirItem(tree, repositoryFile).expand().getNode(PROJ1).select();
		ContextMenuHelper.clickContextMenu(tree, myUtil
				.getPluginLocalizedValue("ImportProjectsCommand"));
		SWTBotShell shell = bot.shell(wizardTitle);
		shell = bot.shell(wizardTitle);
		activateItemByKeyboard(shell,
				UIText.GitSelectWizardPage_ImportExistingButton);
		activateItemByKeyboard(shell,
				UIText.GitSelectWizardPage_AutoShareButton);
		shell.bot().button(IDialogConstants.NEXT_LABEL).click();
		waitInUI();
		shell.bot().tree().getAllItems()[0].check();
		shell.bot().checkBox().select();
		shell.bot().button("Select...").click();
		SWTBotShell workingSetDialog = bot.shell("Select Working Sets");
		workingSetDialog.bot().button("New...").click();
		SWTBotShell newDialog = bot.shell("New Working Set");
		newDialog.bot().button(IDialogConstants.NEXT_LABEL).click();
		String workingSetName = "myWorkingSet";
		newDialog.bot().text(0).setText(workingSetName);
		newDialog.bot().button(IDialogConstants.FINISH_LABEL).click();
		workingSetDialog.bot().table().getTableItem(workingSetName).check();
		workingSetDialog.bot().button(IDialogConstants.OK_LABEL).click();
		shell.bot().button(IDialogConstants.FINISH_LABEL).click();
		waitInUI();
		assertProjectExistence(PROJ1, true);
		assertProjectInWorkingSet(workingSetName, PROJ1);
	}

	private void assertProjectInWorkingSet(String workingSetName,
			String projectName) {
		IWorkingSetManager workingSetManager = PlatformUI.getWorkbench().getWorkingSetManager();
		IWorkingSet workingSet = workingSetManager.getWorkingSet(workingSetName);
		IAdaptable[] elements = workingSet.getElements();
		assertEquals("Wrong number of projects in working set", 1, elements.length);
		IProject project = (IProject) elements[0].getAdapter(IProject.class);
		assertEquals("Wrong project in working set", projectName, project.getName());
	}

