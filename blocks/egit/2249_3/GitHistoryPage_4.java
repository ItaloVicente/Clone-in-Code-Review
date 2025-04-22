	private void initActions() {
		try {
			showAllFilter = ShowFilter.valueOf(Activator.getDefault()
					.getPreferenceStore().getString(PREF_SHOWALLFILTER));
		} catch (IllegalArgumentException e) {
			showAllFilter = ShowFilter.SHOWALLRESOURCE;
		}

		actions = new GitHistoryPageActions(this);
		setupToolBar();
		setupViewMenu();

		graph.getControl().addMenuDetectListener(new MenuDetectListener() {
			public void menuDetected(MenuDetectEvent e) {
				popupMgr.add(actions.showFilesAction);
				popupMgr.add(actions.showCommentAction);
			}
		});
	}

	private void setupToolBar() {
		IToolBarManager mgr = getSite().getActionBars().getToolBarManager();
		mgr.add(actions.findAction);
		mgr.add(new Separator());
		mgr.add(actions.showAllRepoVersionsAction);
		mgr.add(actions.showAllProjectVersionsAction);
		mgr.add(actions.showAllFolderVersionsAction);
		mgr.add(actions.showAllResourceVersionsAction);
		mgr.add(new Separator());
		mgr.add(actions.compareModeAction);
		mgr.add(actions.showAllBranchesAction);
	}

	private void setupViewMenu() {
		IMenuManager viewMenuMgr = getSite().getActionBars().getMenuManager();
		viewMenuMgr.add(actions.refreshAction);

		viewMenuMgr.add(new Separator());
		IMenuManager showSubMenuMgr = new MenuManager(
				UIText.GitHistoryPage_ShowSubMenuLabel);
		viewMenuMgr.add(showSubMenuMgr);
		showSubMenuMgr.add(actions.showAllBranchesAction);
		showSubMenuMgr.add(actions.findAction);
		showSubMenuMgr.add(actions.showFilesAction);
		showSubMenuMgr.add(actions.showCommentAction);

		IMenuManager filterSubMenuMgr = new MenuManager(
				UIText.GitHistoryPage_FilterSubMenuLabel);
		viewMenuMgr.add(filterSubMenuMgr);
		filterSubMenuMgr.add(actions.showAllRepoVersionsAction);
		filterSubMenuMgr.add(actions.showAllProjectVersionsAction);
		filterSubMenuMgr.add(actions.showAllFolderVersionsAction);
		filterSubMenuMgr.add(actions.showAllResourceVersionsAction);

		viewMenuMgr.add(new Separator());
		viewMenuMgr.add(actions.compareModeAction);
		viewMenuMgr.add(actions.reuseCompareEditorAction);

		viewMenuMgr.add(new Separator());
		viewMenuMgr.add(actions.wrapCommentAction);
		viewMenuMgr.add(actions.fillCommentAction);
	}

