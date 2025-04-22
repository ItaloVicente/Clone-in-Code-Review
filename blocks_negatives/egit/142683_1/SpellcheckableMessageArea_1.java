	private static class TextViewerAction extends Action implements IUpdate {

		private int fOperationCode= -1;
		private ITextOperationTarget fOperationTarget;

		/**
		 * Creates a new action.
		 *
		 * @param target
		 *            to operate on
		 * @param operationCode
		 *            the opcode
		 */
		public TextViewerAction(ITextOperationTarget target,
				int operationCode) {
			fOperationCode= operationCode;
			fOperationTarget = target;
			update();
		}

		/**
		 * Updates the enabled state of the action.
		 */
		@Override
		public void update() {
			if (fOperationCode == ITextOperationTarget.REDO) {
				return;
			}
			setEnabled(fOperationTarget != null
					&& fOperationTarget.canDoOperation(fOperationCode));
		}

		/**
		 * @see Action#run()
		 */
		@Override
		public void run() {
			if (fOperationCode != -1 && fOperationTarget != null)
				fOperationTarget.doOperation(fOperationCode);
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

		@Override
		public void propertyChange(PropertyChangeEvent event) {
			if (event.getProperty().equals(getPreferenceKey()))
				synchronizeWithPreference();
		}

		protected void synchronizeWithPreference() {
			if (!viewer.getTextWidget().getEditable()) {
				return;
			}
			boolean checked = false;
			if (store != null)
				checked = store.getBoolean(getPreferenceKey());
			if (checked != isChecked()) {
				setChecked(checked);
				toggleState(checked);
			} else if (checked) {
				toggleState(false);
				toggleState(true);
			}
		}

		protected String getPreferenceKey() {
			return preferenceKey;
		}

		@Override
		public void run() {
			toggleState(isChecked());
			if (store != null)
				store.setValue(getPreferenceKey(), isChecked());
		}

		public void dispose() {
			if (store != null)
				store.removePropertyChangeListener(this);
		}

		/**
		 * @param checked
		 *            new state
		 */
		abstract protected void toggleState(boolean checked);

		protected ITextViewer getTextViewer() {
			return viewer;
		}

		protected IPreferenceStore getStore() {
			return store;
		}
	}

