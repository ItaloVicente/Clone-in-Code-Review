	private static abstract class BooleanPrefAction extends Action implements
			IPropertyChangeListener, IWorkbenchAction {
		private final String prefName;

		private final IPersistentPreferenceStore store;

		BooleanPrefAction(final IPersistentPreferenceStore store,
				final String pn, final String text) {
			this.store = store;
			setText(text);
			prefName = pn;
			store.addPropertyChangeListener(this);
			setChecked(store.getBoolean(prefName));
		}

		public void run() {
			store.setValue(prefName, isChecked());
			if (store.needsSaving()) {
				try {
					store.save();
				} catch (IOException e) {
					Activator.handleError(e.getMessage(), e, false);
				}
			}
		}

		abstract void apply(boolean value);

		public void propertyChange(final PropertyChangeEvent event) {
			if (prefName.equals(event.getProperty())) {
				setChecked(store.getBoolean(prefName));
				apply(isChecked());
			}
		}

		public void dispose() {
			store.removePropertyChangeListener(this);
		}
	}

