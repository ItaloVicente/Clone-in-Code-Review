	private boolean compatModeCheck(int step, IConfigurationElement ce) {

		if (step == BEFORE_WORKBENCH_MODEL) {
			if (ce.getAttribute(COMPATIBILITY) == null) {
				return true;
			}
			return Boolean.FALSE.toString().equals(ce.getAttribute(COMPATIBILITY));
		}

		else if (step == AFTER_WORKBENCH_MODEL) {
			if (ce.getAttribute(COMPATIBILITY) == null) {
				return false;
			}
			return Boolean.TRUE.toString().equals(ce.getAttribute(COMPATIBILITY));
		}

		return false;
	}

