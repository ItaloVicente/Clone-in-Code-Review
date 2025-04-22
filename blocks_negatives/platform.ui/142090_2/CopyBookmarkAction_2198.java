    /**
     * Creates the action.
     *
     * @param bookmarkNavigator the view
     */
    public CopyBookmarkAction(BookmarkNavigator bookmarkNavigator) {
        super(bookmarkNavigator, BookmarkMessages.CopyBookmark_text);
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
                IBookmarkHelpContextIds.COPY_BOOKMARK_ACTION);
        setEnabled(false);
    }
