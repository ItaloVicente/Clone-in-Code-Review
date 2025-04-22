		IMenuManager showSubMenuMgr = new MenuManager(
				UIText.GitHistoryPage_ShowSubMenuLabel);
		viewMenuMgr.add(showSubMenuMgr);
		showSubMenuMgr.add(showAllBranchesAction);
		showSubMenuMgr.add(findAction);
		showSubMenuMgr.add(showFilesAction);
		showSubMenuMgr.add(showCommentAction);

		IMenuManager filterSubMenuMgr = new MenuManager(
				UIText.GitHistoryPage_FilterSubMenuLabel);
		viewMenuMgr.add(filterSubMenuMgr);
		filterSubMenuMgr.add(showAllRepoVersionsAction);
		filterSubMenuMgr.add(showAllProjectVersionsAction);
		filterSubMenuMgr.add(showAllFolderVersionsAction);
		filterSubMenuMgr.add(showAllResourceVersionsAction);

