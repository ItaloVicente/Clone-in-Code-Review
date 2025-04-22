		showAllResourceVersionsAction
				.setChecked(showAllFilter == showAllResourceVersionsAction.filter);

		IToolBarManager mgr = getSite().getActionBars().getToolBarManager();
		mgr.add(new Separator());
		mgr.add(showAllRepoVersionsAction);
		mgr.add(showAllProjectVersionsAction);
		mgr.add(showAllFolderVersionsAction);
		mgr.add(showAllResourceVersionsAction);

		IMenuManager viewMenuMgr = getSite().getActionBars().getMenuManager();
		viewMenuMgr.add(new Separator());
		viewMenuMgr.add(showAllRepoVersionsAction);
		viewMenuMgr.add(showAllProjectVersionsAction);
		viewMenuMgr.add(showAllFolderVersionsAction);
		viewMenuMgr.add(showAllResourceVersionsAction);
