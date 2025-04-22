		Repository[] repositories = SelectionUtils
				.getRepositories(handlerService.getCurrentState());

		if (repositories.length > 0) {
			createDynamicMenu(menu, repositories);
		}
	}

	private void createDynamicMenu(Menu menu, final Repository[] repositories) {

		if (!isMultipleSelection(repositories))
		{
			createNewBranchMenuItem(menu, repositories[0]);
			createSeparator(menu);
		}

		int itemCount = createMostActiveBranchesMenuItems(menu, repositories);

		if (!isMultipleSelection(repositories) && itemCount > 0) {
			createSeparator(menu);
			createOtherMenuItem(menu, repositories[0]);
		}

		if (itemCount == 0 && isMultipleSelection(repositories)) {
			createDisabledMenu(menu, UIText.SwitchToMenu_NoCommonBranchesFound);
		}
