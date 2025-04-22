	public static boolean isImpExpEnabled() {
		if (impExpEnabled == null) {
			if (propertyStr == null) {
				impExpEnabled = true;
			} else {
				impExpEnabled = Boolean.parseBoolean(propertyStr);
			}
		}
		return impExpEnabled;
	}

