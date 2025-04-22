		IEditorPart oldEditor = getActiveEditor();
		super.activateEditor(part);
		updateGradient(oldEditor);
	}

	protected boolean getShellActivated() {
		WorkbenchWindow window = (WorkbenchWindow) getSite().getPage().getWorkbenchWindow();
		return window.getShellActivated();
	}
