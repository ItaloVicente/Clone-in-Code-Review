    private IPropertyListener editorRegistryListener = new IPropertyListener() {
		@Override
		public void propertyChanged(Object source, int propId) {
			if (propId == IEditorRegistry.PROP_CONTENTS) {
				fireLabelProviderChanged(new LabelProviderChangedEvent(WorkbenchLabelProvider.this));
			}
