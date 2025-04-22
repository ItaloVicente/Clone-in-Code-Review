	private IPropertyListener editorPropertyListener = (source, propId) -> {
		if (propId == IWorkbenchPartConstants.PROP_TITLE) {
			if (lastActiveEditor != null) {
				String newTitle = lastActiveEditor.getTitleToolTip();
				if (!lastEditorTitleTooltip.equals(newTitle)) {
					recomputeTitle();
