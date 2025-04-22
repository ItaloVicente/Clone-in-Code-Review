	private FileSystemElement root;

	CheckboxTreeAndListGroup selectionGroup;

	private boolean expandAllOnOpen = false;

	private static final int SIZING_SELECTION_WIDGET_WIDTH = 500;

	private static final int SIZING_SELECTION_WIDGET_HEIGHT = 250;

	public FileSelectionDialog(Shell parentShell, FileSystemElement fileSystemElement, String message) {
		super(parentShell);
		setTitle(IDEWorkbenchMessages.FileSelectionDialog_title);
		root = fileSystemElement;
		if (message != null) {
