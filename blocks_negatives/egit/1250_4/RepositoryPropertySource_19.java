	private void setConfigValue(StoredConfig configuration, String key,
			String value) throws IOException {
		StringTokenizer tok = new StringTokenizer(key, "."); //$NON-NLS-1$
		if (tok.countTokens() == 2) {
			if (value == null || value.length() == 0) {
				configuration.unset(tok.nextToken(), null, tok.nextToken());
			} else {
				configuration.setString(tok.nextToken(), null, tok.nextToken(),
						value);
			}
