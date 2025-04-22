    private IAdaptable root;

    private CheckboxTreeAndListGroup selectionGroup;

    private final static int SIZING_SELECTION_WIDGET_WIDTH = 400;

    private final static int SIZING_SELECTION_WIDGET_HEIGHT = 300;

    /**
     * Creates a resource selection dialog rooted at the given element.
     *
     * @param parentShell the parent shell
     * @param rootElement the root element to populate this dialog with
     * @param message the message to be displayed at the top of this dialog, or
     *    <code>null</code> to display a default message
     */
    public ResourceSelectionDialog(Shell parentShell, IAdaptable rootElement,
            String message) {
        super(parentShell);
        setTitle(IDEWorkbenchMessages.ResourceSelectionDialog_title);
        root = rootElement;
        if (message != null) {
