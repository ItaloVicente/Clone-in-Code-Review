		public void modifyText(ModifyEvent e) {
			updateDateFormatPreview();
			setChanged();
			notifyObservers();
		}
	}

	private class FormatEditor extends SelectionAdapter {
		private final Text text;

		private final Map bindings;

		private final String key;
