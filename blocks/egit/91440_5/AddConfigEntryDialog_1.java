	private boolean isValidKey(String keyValue) {
		return keyValue != null && VALID_KEY.matcher(keyValue).matches();
	}

	private String trimKey(String keyValue) {
		return keyValue.replaceAll("^(?:\\h|\\v)*|(?:\\h|\\v)*$", ""); //$NON-NLS-1$ //$NON-NLS-2$
	}

