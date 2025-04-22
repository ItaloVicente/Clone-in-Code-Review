
		MenuManager recent = new MenuManager(WorkbenchMessages.OpenRecent_text);
		recent.add(ContributionItemFactory.REOPEN_EDITORS.create(getWindow()));
		recent.add(new GroupMarker(IWorkbenchActionConstants.MRU));
		menu.add(recent);

