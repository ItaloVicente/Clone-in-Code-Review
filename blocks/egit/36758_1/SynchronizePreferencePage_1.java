
	private BooleanFieldEditor createBooleanFieldEditorWithUIPreferenceStore(
			final String name, final String label) {
		return new BooleanFieldEditor(name, label, getFieldEditorParent()) {
			public IPreferenceStore getPreferenceStore() {
				return org.eclipse.egit.ui.Activator.getDefault()
						.getPreferenceStore();
			}
		};
	}
