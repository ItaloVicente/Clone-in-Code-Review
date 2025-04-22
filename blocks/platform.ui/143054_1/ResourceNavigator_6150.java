		if (getActionGroup() != null) {
			getActionGroup().dispose();
		}
		Control control = viewer.getControl();
		if (dragDetectListener != null && control != null && control.isDisposed() == false) {
			control.removeListener(SWT.DragDetect, dragDetectListener);
		}

		super.dispose();
	}

	protected void editorActivated(IEditorPart editor) {
		if (!isLinkingEnabled()) {
			return;
		}

		IFile file = ResourceUtil.getFile(editor.getEditorInput());
		if (file != null) {
			ISelection newSelection = new StructuredSelection(file);
