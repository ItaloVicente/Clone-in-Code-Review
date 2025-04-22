	private String editorId;
	private String filename;
	private IWorkbenchWindow window;
	private Composite ctrl;

	public EditorWidgetFactory(String filename) {
		this.filename = filename;
		this.editorId = null;
	}

	public EditorWidgetFactory(String filename, String editorId) {
		this.filename = filename;
		this.editorId = editorId;
	}

	public static Composite getControl(IEditorPart part) {
