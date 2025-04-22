    /**
     * Create a new instance of this class.
     *
     * @param view the view
     */
    public SelectAllAction(BookmarkNavigator view) {
        super(view, BookmarkMessages.SelectAll_text);
        setToolTipText(BookmarkMessages.SelectAll_toolTip);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
                IBookmarkHelpContextIds.SELECT_ALL_BOOKMARK_ACTION);
        setEnabled(true);
    }
