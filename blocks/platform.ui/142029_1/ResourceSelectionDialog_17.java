	private IAdaptable root;

	private CheckboxTreeAndListGroup selectionGroup;

	private static final int SIZING_SELECTION_WIDGET_WIDTH = 400;

	private static final int SIZING_SELECTION_WIDGET_HEIGHT = 300;

	public ResourceSelectionDialog(Shell parentShell, IAdaptable rootElement, String message) {
		super(parentShell);
		setTitle(IDEWorkbenchMessages.ResourceSelectionDialog_title);
		root = rootElement;
		if (message != null) {
