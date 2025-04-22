	private boolean compatModeCheck(int step, IConfigurationElement ce) {

		if (step == E4ONLY) {
			return true;
		}

		if (step == E3_E4STEP) {
			return !Boolean.parseBoolean(ce.getAttribute(E3COMPATIBILITY));
		}

		if (step == E3_E3STEP) {
			return Boolean.parseBoolean(ce.getAttribute(E3COMPATIBILITY));
		}

		return false;
	}

