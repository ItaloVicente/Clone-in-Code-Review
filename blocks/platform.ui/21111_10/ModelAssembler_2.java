	private boolean postModelCreationCheck(int step, IConfigurationElement ce) {

		if (step == DURING_MODEL_CREATION) {
			if (ce.getAttribute(POST_MODEL_CREATION) == null) {
				return true;
			}
			return Boolean.FALSE.toString().equals(ce.getAttribute(POST_MODEL_CREATION));
		}

		else if (step == AFTER_MODEL_CREATION) {
			if (ce.getAttribute(POST_MODEL_CREATION) == null) {
				return false;
			}
			return Boolean.TRUE.toString().equals(ce.getAttribute(POST_MODEL_CREATION));
		}

		return false;
	}

