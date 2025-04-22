    /**
     * Create a new instance of this class.
     *
     * @param view the view
     */
    public RemoveBookmarkAction(BookmarkNavigator view) {
        super(view, BookmarkMessages.RemoveBookmark_text);
        setToolTipText(BookmarkMessages.RemoveBookmark_toolTip);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
                IBookmarkHelpContextIds.REMOVE_BOOKMARK_ACTION);
        setEnabled(false);
    }
