		copyAction = createStandardAction(ActionFactory.COPY);
		selectAllAction = createStandardAction(ActionFactory.SELECT_ALL);

		showCommentAction = createShowComment();
		showFilesAction = createShowFiles();
		wrapCommentAction = createCommentWrap();
		fillCommentAction = createCommentFill();

		wrapCommentAction.setEnabled(showCommentAction.isChecked());
		fillCommentAction.setEnabled(showCommentAction.isChecked());

		final IMenuManager menuManager = getSite().getActionBars()
				.getMenuManager();

		menuManager.add(showFilesAction);
		menuManager.add(showCommentAction);

		menuManager.add(new Separator());

		menuManager.add(wrapCommentAction);
		menuManager.add(fillCommentAction);
