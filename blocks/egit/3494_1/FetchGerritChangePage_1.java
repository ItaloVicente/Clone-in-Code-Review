
	private void storeLastUsedUri(String uri) {
		settings.put(lastUriKey, uri.trim());
	}

	private void selectLastUsedUri() {
		String lastUri = settings.get(lastUriKey);
		if (lastUri != null) {
			int i = uriCombo.indexOf(lastUri);
			if (i != -1) {
				uriCombo.select(i);
				return;
			}
		}
		uriCombo.select(0);
	}

