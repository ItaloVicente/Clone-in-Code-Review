		Object element = selection.getFirstElement();

		if (element instanceof IResource) {
			menu.appendToGroup(GIT_ACTIONS, createItem(COMMIT_ACTION));
			menu.appendToGroup(GIT_ACTIONS, createItem(ADD_TO_INDEX));
			menu.appendToGroup(GIT_ACTIONS, createItem(IGNORE_ACTION));
		} else if (element instanceof GitModelObject && selection.size() == 1)
			createMenuForGitModelObject(menu, (GitModelObject) element);

		IContributionItem fileGroup = findGroup(menu,
