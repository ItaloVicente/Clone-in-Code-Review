    /**
     * The constructor.
     *
     * @param view the view
     */
    public PasteBookmarkAction(BookmarkNavigator view) {
        super(view, BookmarkMessages.PasteBookmark_text);
        this.view = view;
        PlatformUI.getWorkbench().getHelpSystem().setHelp(this,
                IBookmarkHelpContextIds.PASTE_BOOKMARK_ACTION);
        setEnabled(false);
    }
