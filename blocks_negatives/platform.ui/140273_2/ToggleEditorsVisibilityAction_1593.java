    @Override
	public void perspectiveActivated(IWorkbenchPage page,
            IPerspectiveDescriptor perspective) {
        if (page.isEditorAreaVisible()) {
            setText(WorkbenchMessages.ToggleEditor_hideEditors);
        } else {
            setText(WorkbenchMessages.ToggleEditor_showEditors);
        }
    }
