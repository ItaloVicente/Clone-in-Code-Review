		ActionContributionItem openPreferencesItem = new ActionContributionItem(
				openPreferencesAction);
		openPreferencesItem.setVisible(!Util.isMac());
		menu.add(openPreferencesItem);

		menu.add(ContributionItemFactory.OPEN_WINDOWS.create(getWindow()));
		return menu;
	}
