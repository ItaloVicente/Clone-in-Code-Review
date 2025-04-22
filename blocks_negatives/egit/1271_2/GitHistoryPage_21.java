	private void createViewMenu() {
		final IActionBars actionBars = getSite().getActionBars();
		final IMenuManager menuManager = actionBars.getMenuManager();
		IAction a;

		a = createCommentWrap();
		menuManager.add(a);
		popupMgr.add(a);

		a = createCommentFill();
		menuManager.add(a);
		popupMgr.add(a);

		menuManager.add(new Separator());
		popupMgr.add(new Separator());

		a = createShowComment();
		menuManager.add(a);
		popupMgr.add(a);

		a = createShowFiles();
		menuManager.add(a);
		popupMgr.add(a);

		menuManager.add(new Separator());
		popupMgr.add(new Separator());
	}

