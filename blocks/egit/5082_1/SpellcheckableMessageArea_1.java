
		private String getPreferenceKey() {
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

		abstract protected void toggleState(boolean checked);

		protected ITextViewer getTextViewer() {
			return viewer;
		}
