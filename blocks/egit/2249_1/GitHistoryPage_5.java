	
		actions = new GitHistoryPageActions(this);
		setupToolBar();
		setupViewMenu();
	
		graph.getControl().addMenuDetectListener(new MenuDetectListener() {
			public void menuDetected(MenuDetectEvent e) {
				popupMgr.add(actions.showFilesAction);
				popupMgr.add(actions.showCommentAction);
