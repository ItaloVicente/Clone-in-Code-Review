		if (selection.size() == 1) {
			Object element = selection.getFirstElement();

			if (element instanceof GitModelObject)
				createMenuForGitModelObject(menu, (GitModelObject) element);
			else {
				menu.appendToGroup(GIT_ACTIONS, createItem(COMMIT_ACTION));
				menu.appendToGroup(GIT_ACTIONS, createItem(ADD_TO_INDEX));
				menu.appendToGroup(GIT_ACTIONS, createItem(IGNORE_ACTION));
			}

			IContributionItem fileGroup = findGroup(menu,
