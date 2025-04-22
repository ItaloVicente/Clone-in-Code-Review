    /**
     * Constructs a new file extension dialog.
     *
     * @param parentShell the parent shell
     * @param title the dialog title
     * @param helpContextId the help context for this dialog
     * @param headerTitle the dialog header
     * @param message the dialog message
     * @param label the label for the "file type" field
     * @since 3.4
     */
    public ContentTypeFilenameAssociationDialog(Shell parentShell, String title, String helpContextId, String headerTitle, String message, String label) {
    	super(parentShell);
    	this.title = title;
    	this.helpContextId = helpContextId;
