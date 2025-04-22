		return defaultEnabled;
	}

	boolean setDefaultEnabled(boolean defaultEnabled) {
		if (!Util.equals(defaultEnabled, this.defaultEnabled)) {
			this.defaultEnabled = defaultEnabled;
			hashCode = HASH_INITIAL;
			string = null;
			return true;
		}

		return false;
	}
