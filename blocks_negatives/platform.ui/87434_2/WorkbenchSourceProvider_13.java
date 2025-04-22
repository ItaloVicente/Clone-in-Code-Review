	private IPropertyListener editorListener = new IPropertyListener() {
		@Override
		public void propertyChanged(Object source, int propId) {
			if (propId == IEditorPart.PROP_INPUT) {
				handleInputChanged((IEditorPart) source);
			}
