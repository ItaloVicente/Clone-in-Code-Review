		final IAction showCommentAction = createShowComment();
		final IAction showFilesAction = createShowFiles();

		wrapCommentAction = createCommentWrap();
		fillCommentAction = createCommentFill();

		wrapCommentAction.setEnabled(showCommentAction.isChecked());
		fillCommentAction.setEnabled(showCommentAction.isChecked());
