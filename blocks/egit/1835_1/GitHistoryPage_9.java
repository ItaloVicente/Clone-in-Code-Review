
		final IAction showCommentAction = createShowComment();
		final IAction showFilesAction = createShowFiles();
		wrapCommentAction = createCommentWrap();
		fillCommentAction = createCommentFill();

		wrapCommentAction.setEnabled(showCommentAction.isChecked());
		fillCommentAction.setEnabled(showCommentAction.isChecked());

		viewMenuMgr.add(showFilesAction);
		viewMenuMgr.add(showCommentAction);

		viewMenuMgr.add(new Separator());

		viewMenuMgr.add(wrapCommentAction);
		viewMenuMgr.add(fillCommentAction);

		graph.getControl().addMenuDetectListener(new MenuDetectListener() {

			public void menuDetected(MenuDetectEvent e) {
				popupMgr.add(showFilesAction);
				popupMgr.add(showCommentAction);

			}
		});
