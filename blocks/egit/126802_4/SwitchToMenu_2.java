		Repository[] repositories = SelectionUtils
				.getRepositories(handlerService.getCurrentState());

		if (repositories.length > 0) {
			createDynamicMenu(menu, repositories);
		}
