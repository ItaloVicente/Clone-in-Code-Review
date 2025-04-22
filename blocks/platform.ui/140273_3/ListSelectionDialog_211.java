	private Object inputElement;

	private ILabelProvider labelProvider;

	private IStructuredContentProvider contentProvider;

	CheckboxTableViewer listViewer;

	private static final int SIZING_SELECTION_WIDGET_HEIGHT = 250;

	private static final int SIZING_SELECTION_WIDGET_WIDTH = 300;

	public ListSelectionDialog(Shell parentShell, Object input, IStructuredContentProvider contentProvider,
			ILabelProvider labelProvider, String message) {
		super(parentShell);
		setTitle(WorkbenchMessages.ListSelection_title);
		inputElement = input;
		this.contentProvider = contentProvider;
		this.labelProvider = labelProvider;
		if (message != null) {
