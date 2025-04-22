	private IEditorInput input;

	private IEditorDescriptor descriptor;

	private IMemento memento;

	public EditorHistoryItem(IEditorInput input, IEditorDescriptor descriptor) {
		this.input = input;
		this.descriptor = descriptor;
	}

	public EditorHistoryItem(IMemento memento) {
		this.memento = memento;
	}

	public IEditorDescriptor getDescriptor() {
		return descriptor;
	}

	public IEditorInput getInput() {
		return input;
	}

	public boolean isRestored() {
		return memento == null;
	}

	public String getName() {
		if (isRestored() && getInput() != null) {
			return getInput().getName();
		} else if (memento != null) {
			String name = memento.getString(IWorkbenchConstants.TAG_NAME);
			if (name != null) {
				return name;
			}
		}
		return ""; //$NON-NLS-1$
	}

	public String getToolTipText() {
		if (isRestored() && getInput() != null) {
			return getInput().getToolTipText();
		} else if (memento != null) {
			String name = memento.getString(IWorkbenchConstants.TAG_TOOLTIP);
			if (name != null) {
				return name;
			}
		}
		return ""; //$NON-NLS-1$
	}

	public boolean matches(IEditorInput input) {
		if (isRestored()) {
