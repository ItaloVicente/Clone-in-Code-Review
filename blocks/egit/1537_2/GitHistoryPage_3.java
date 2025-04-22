		IToolBarManager mgr = getSite().getActionBars().getToolBarManager();
		IMenuManager viewMenuMgr = getSite().getActionBars().getMenuManager();

		mgr.add(findAction);
		mgr.add(new Separator());
		mgr.add(showAllRepoVersionsAction);
		mgr.add(showAllProjectVersionsAction);
		mgr.add(showAllFolderVersionsAction);
		mgr.add(showAllResourceVersionsAction);
		mgr.add(new Separator());
		mgr.add(compareModeAction);
		mgr.add(showAllBranchesAction);

		viewMenuMgr.add(refreshAction);
		viewMenuMgr.add(findAction);
		viewMenuMgr.add(new Separator());
		viewMenuMgr.add(showAllRepoVersionsAction);
		viewMenuMgr.add(showAllProjectVersionsAction);
		viewMenuMgr.add(showAllFolderVersionsAction);
		viewMenuMgr.add(showAllResourceVersionsAction);
		viewMenuMgr.add(new Separator());
		viewMenuMgr.add(compareModeAction);
		viewMenuMgr.add(showAllBranchesAction);
	}
