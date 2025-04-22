    /**
     * Creates the dialog.  By default this dialog creates a new bookmark.
     * To set the resource and initial attributes for the new bookmark,
     * use <code>setResource</code> and <code>setInitialAttributes</code>.
     * To show or modify an existing bookmark, use <code>setMarker</code>.
     *
     * @param parentShell the parent shell
     */
    public BookmarkPropertiesDialog(Shell parentShell) {
        this(parentShell, BookmarkMessages.PropertiesDialogTitle_text);
    }

    /**
     * Creates the dialog.  By default this dialog creates a new bookmark.
     * To set the resource and initial attributes for the new bookmark,
     * use <code>setResource</code> and <code>setInitialAttributes</code>.
     * To show or modify an existing bookmark, use <code>setMarker</code>.
     *
     * @param parentShell the parent shell
     * @param title the title for the dialog
     */
    public BookmarkPropertiesDialog(Shell parentShell, String title) {
        super(parentShell, title);
    	setType(IMarker.BOOKMARK);
    }

    /**
     * Sets the marker to show or modify.
     *
     * @param marker the marker, or <code>null</code> to create a new marker
     */
    @Override
