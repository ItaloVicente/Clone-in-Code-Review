    @Override
	public void perspectiveChanged(IWorkbenchPage page,
            IPerspectiveDescriptor perspective, String changeId) {
        if (changeId == IWorkbenchPage.CHANGE_RESET
                || changeId == IWorkbenchPage.CHANGE_EDITOR_AREA_HIDE
                || changeId == IWorkbenchPage.CHANGE_EDITOR_AREA_SHOW) {
            if (page.isEditorAreaVisible()) {
                setText(WorkbenchMessages.ToggleEditor_hideEditors);
            } else {
                setText(WorkbenchMessages.ToggleEditor_showEditors);
            }
        }
    }
