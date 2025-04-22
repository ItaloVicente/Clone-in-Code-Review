	private ITextEditor textEditor;

	private Object[][] customAttributes;

	private String message;

	private static final String MARKER_TYPE = "org.eclipse.ui.examples.readmetool.readmemarker"; //$NON-NLS-1$

	public AddReadmeMarkerAction(ITextEditor editor, String label, Object[][] attributes, String message) {
		textEditor = editor;
		setText(label);
		this.customAttributes = attributes;
		this.message = message;
	}

	@Override
