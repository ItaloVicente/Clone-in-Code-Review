	public final boolean isHandled() {
		if (configurationElement != null && handler == null) {
			return true;
		}

		if (isOkToLoad() && loadHandler()) {
