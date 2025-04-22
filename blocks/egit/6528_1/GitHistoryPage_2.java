		IMenuManager showInMessageManager = new MenuManager(
				UIText.GitHistoryPage_InRevisionCommentSubMenuLabel);
		showSubMenuMgr.add(showInMessageManager);
		showInMessageManager.add(actions.showTagSequenceAction);
		showInMessageManager.add(actions.wrapCommentAction);
		showInMessageManager.add(actions.fillCommentAction);

