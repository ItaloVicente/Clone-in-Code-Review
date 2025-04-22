	protected EditBookmarkAction(BookmarkNavigator view) {
		super(view, BookmarkMessages.Properties_text);
		PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
				IBookmarkHelpContextIds.BOOKMARK_PROPERTIES_ACTION);
		setEnabled(false);
	}
