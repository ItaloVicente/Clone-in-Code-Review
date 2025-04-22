	@Test
	public void testRebaseDialogOnRepo() throws Exception {
		ICommandService srv = CommonUtils.getService(PlatformUI.getWorkbench(),
				ICommandService.class);
		State commandState = srv.getCommand(ToggleBranchHierarchyCommand.ID)
				.getState(ToggleBranchHierarchyCommand.TOGGLE_STATE);
		Boolean isHierarchical = (Boolean) commandState.getValue();
		commandState.setValue(Boolean.TRUE);
		try {
			SWTBotTree tree = getOrOpenView().bot().tree();

			myRepoViewUtil.getRootItem(tree, clonedRepositoryFile).select();

			ContextMenuHelper.clickContextMenu(tree,
					myUtil.getPluginLocalizedValue("RebaseCommand.label2"));

			String title = MessageFormat.format(
					UIText.RebaseTargetSelectionDialog_RebaseTitleWithBranch,
					FileRepositoryBuilder.create(clonedRepositoryFile)
							.getBranch());

			SWTBotShell targetSelectionDialog = bot.shell(title);
			SWTBot dialogBot = targetSelectionDialog.bot();
			SWTBotTree dialogTree = dialogBot.tree();
			assertEquals("Should have a node selected", 1,
					dialogTree.selectionCount());
			String[] result = { null };
			PlatformUI.getWorkbench().getDisplay().syncExec(() -> {
				TreeItem[] selected = dialogTree.widget.getSelection();
				result[0] = selected[0].getText();
			});
			assertTrue("master node should be selected",
					result[0] != null && result[0].contains("master"));
			targetSelectionDialog.close();
		} finally {
			commandState.setValue(isHierarchical);
		}
	}

