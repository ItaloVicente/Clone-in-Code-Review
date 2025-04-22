		super.partDeactivated(part);
		if (part instanceof IEditorPart) {
			updateActiveEditor();
			updateState();
		}
	}

	private void setActiveEditor(IEditorPart part) {
		if (activeEditor == part) {
			return;
		}
		if (activeEditor != null) {
			editorDeactivated(activeEditor);
		}
		activeEditor = part;
		if (activeEditor != null) {
			editorActivated(activeEditor);
		}
	}

	private void updateActiveEditor() {
		if (getActivePage() == null) {
			setActiveEditor(null);
		} else {
			setActiveEditor(getActivePage().getActiveEditor());
		}
	}

	protected void updateState() {
		setEnabled(getActiveEditor() != null);
	}
