		MenuManager showInSubMenu = new MenuManager(
				UIText.CommitFileDiffViewer_ShowInMenuLabel);
		showInSubMenu.add(ContributionItemFactory.VIEWS_SHOW_IN
				.create(site.getWorkbenchWindow()));

		mgr.add(new Separator());
		mgr.add(showInSubMenu);

