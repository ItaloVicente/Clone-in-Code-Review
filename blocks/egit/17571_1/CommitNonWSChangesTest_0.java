		SWTBotToolbarToggleButton showUntracked = commitDialog.bot()
				.toolbarToggleButtonWithTooltip(
						UIText.CommitDialog_ShowUntrackedFiles);
		if (!showUntracked.isChecked())
			showUntracked.select();

		SWTBotTree tree = commitDialog.bot().tree();
		assertEquals("Wrong row count", 4, tree.rowCount());
		assertTreeLineContent(tree, 0, "GeneralProject/.project");
		assertTreeLineContent(tree, 1, "GeneralProject/folder/test.txt");
		assertTreeLineContent(tree, 2, "GeneralProject/folder/test2.txt");
		assertTreeLineContent(tree, 3, "ProjectWithoutDotProject/.project");
