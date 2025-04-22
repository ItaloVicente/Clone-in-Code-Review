	private IPropertyListener editorPropertyListener = new IPropertyListener() {
		@Override
		public void propertyChanged(Object source, int propId) {
			if (propId == IWorkbenchPartConstants.PROP_TITLE) {
				if (lastActiveEditor != null) {
					String newTitle= lastActiveEditor.getTitleToolTip();
					if (!lastEditorTitleTooltip.equals(newTitle)) {
						recomputeTitle();
					}
