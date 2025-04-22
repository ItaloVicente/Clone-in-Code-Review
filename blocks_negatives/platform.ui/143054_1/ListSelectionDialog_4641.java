    private Object inputElement;

    private ILabelProvider labelProvider;

    private IStructuredContentProvider contentProvider;

    CheckboxTableViewer listViewer;

    private final static int SIZING_SELECTION_WIDGET_HEIGHT = 250;

    private final static int SIZING_SELECTION_WIDGET_WIDTH = 300;

    /**
     * Creates a list selection dialog.
     *
     * @param parentShell the parent shell
     * @param input	the root element to populate this dialog with
     * @param contentProvider the content provider for navigating the model
     * @param labelProvider the label provider for displaying model elements
     * @param message the message to be displayed at the top of this dialog, or
     *    <code>null</code> to display a default message
     */
    public ListSelectionDialog(Shell parentShell, Object input,
            IStructuredContentProvider contentProvider,
            ILabelProvider labelProvider, String message) {
        super(parentShell);
        setTitle(WorkbenchMessages.ListSelection_title);
        inputElement = input;
        this.contentProvider = contentProvider;
        this.labelProvider = labelProvider;
        if (message != null) {
