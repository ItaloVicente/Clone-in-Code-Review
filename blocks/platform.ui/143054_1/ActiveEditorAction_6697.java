	private IEditorPart activeEditor;

	protected ActiveEditorAction(String text, IWorkbenchWindow window) {
		super(text, window);
		updateState();
	}

	protected void editorActivated(IEditorPart part) {
	}

	protected void editorDeactivated(IEditorPart part) {
	}

	public final IEditorPart getActiveEditor() {
		return activeEditor;
	}

	@Override
