		ActionContributionItem openPreferencesItem = new ActionContributionItem(openPreferencesAction);
		openPreferencesItem.setVisible(!Util.isMac());
		menu.add(openPreferencesItem);

		menu.add(ContributionItemFactory.OPEN_WINDOWS.create(getWindow()));
		return menu;
	}

	private MenuManager addShowView() {
		MenuManager showViewMenuMgr = new MenuManager(IDEWorkbenchMessages.Workbench_showView, "showView"); //$NON-NLS-1$
		IContributionItem showViewMenu = ContributionItemFactory.VIEWS_SHORTLIST.create(getWindow());
		showViewMenuMgr.add(showViewMenu);
		return showViewMenuMgr;
	}
