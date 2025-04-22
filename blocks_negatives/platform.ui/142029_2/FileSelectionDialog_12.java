    private FileSystemElement root;

    CheckboxTreeAndListGroup selectionGroup;

    private boolean expandAllOnOpen = false;

    private static final int SIZING_SELECTION_WIDGET_WIDTH = 500;

    private static final int SIZING_SELECTION_WIDGET_HEIGHT = 250;

    /**
     * Creates a file selection dialog rooted at the given file system element.
     *
     * @param parentShell the parent shell
     * @param fileSystemElement the root element to populate this dialog with
     * @param message the message to be displayed at the top of this dialog, or
     *    <code>null</code> to display a default message
     */
    public FileSelectionDialog(Shell parentShell,
            FileSystemElement fileSystemElement, String message) {
        super(parentShell);
        setTitle(IDEWorkbenchMessages.FileSelectionDialog_title);
        root = fileSystemElement;
        if (message != null) {
