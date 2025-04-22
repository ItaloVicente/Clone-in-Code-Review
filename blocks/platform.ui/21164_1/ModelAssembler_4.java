		if (step == LEGACY_E3STEP) {
			if (E4ONLY.equals(ce.getAttribute(COMPATIBILITY))) {
				return false;
			}
			if (E4STEP.equals(ce.getAttribute(COMPATIBILITY))
					|| ce.getAttribute(COMPATIBILITY) == null) {
				return false;
			}
			if (LEGACYSTEP.equals(ce.getAttribute(COMPATIBILITY))) {
				return true;
			}
			if (LEGACYONLY.equals(ce.getAttribute(COMPATIBILITY))) {
				return true;
			}
			return false;
