		if (step == LEGACY_E4STEP) {
			if (E4ONLY.equals(ce.getAttribute(COMPATIBILITY))) {
				return false;
			}
			if (E4STEP.equals(ce.getAttribute(COMPATIBILITY))
					|| ce.getAttribute(COMPATIBILITY) == null) {
				return true;
			}
			if (LEGACYSTEP.equals(ce.getAttribute(COMPATIBILITY))) {
				return false;
			}
			if (LEGACYONLY.equals(ce.getAttribute(COMPATIBILITY))) {
				return false;
			}
			return false;
