		}
	}

	private static abstract class TextEditorPropertyAction extends Action implements IPropertyChangeListener {

		private SourceViewer viewer;
		private String preferenceKey;
		private IPreferenceStore store;

		public TextEditorPropertyAction(String label, SourceViewer viewer, String preferenceKey) {
			super(label, IAction.AS_CHECK_BOX);
			this.viewer = viewer;
			this.preferenceKey = preferenceKey;
			this.store = EditorsUI.getPreferenceStore();
			if (store != null)
				store.addPropertyChangeListener(this);
			synchronizeWithPreference();
		}

		public void propertyChange(PropertyChangeEvent event) {
			if (event.getProperty().equals(getPreferenceKey()))
				synchronizeWithPreference();
		}

		protected void synchronizeWithPreference() {
			boolean checked = false;
			if (store != null)
				checked = store.getBoolean(getPreferenceKey());
			if (checked != isChecked()) {
				setChecked(checked);
				toggleState(checked);
			} else if (checked) {
				toggleState(false);
				toggleState(true);
