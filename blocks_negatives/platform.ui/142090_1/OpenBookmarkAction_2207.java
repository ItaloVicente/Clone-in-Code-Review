    /**
     * Create a new instance of this class.
     *
     * @param view the view
     */
    public OpenBookmarkAction(BookmarkNavigator view) {
        super(view, BookmarkMessages.OpenBookmark_text);
        setToolTipText(BookmarkMessages.OpenBookmark_toolTip);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
                IBookmarkHelpContextIds.OPEN_BOOKMARK_ACTION);
        setEnabled(false);
    }
