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

	public void dispose() {
		trace = GitTraceLocation.HISTORYVIEW.isActive();
		if (trace)
			GitTraceLocation.getTrace().traceEntry(
					GitTraceLocation.HISTORYVIEW.getLocation());
	
		if (myRefsChangedHandle != null) {
			myRefsChangedHandle.remove();
			myRefsChangedHandle = null;
		}
	
		for (IWorkbenchAction action : actions.actionsToDispose)
			action.dispose();
		actions.actionsToDispose.clear();
		cancelRefreshJob();
		if (popupMgr != null) {
			for (final IContributionItem i : popupMgr.getItems()) {
				if (i instanceof IWorkbenchAction)
					((IWorkbenchAction) i).dispose();
			}
			for (final IContributionItem i : getSite().getActionBars()
					.getMenuManager().getItems()) {
				if (i instanceof IWorkbenchAction)
					((IWorkbenchAction) i).dispose();
			}
		}
		super.dispose();
	}

	@Override
	public void setFocus() {
		graph.getControl().setFocus();
	}

	@Override
	public Control getControl() {
