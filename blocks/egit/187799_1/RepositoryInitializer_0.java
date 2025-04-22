	private void updateTextBufferSize() {
		int bufferSize = preferencesService.getInt(Activator.PLUGIN_ID,
				GitCorePreferences.core_textBufferSize, -1, null);
		if (bufferSize >= 0) {
			RawText.setBufferSize(Math.min(bufferSize, 128 * 1024));
		}
	}

