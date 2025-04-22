		if (step == PURE_E4) {
			if (E4ONLY.equals(ce.getAttribute(COMPATIBILITY))) {
				return true;
			}
			if (E4STEP.equals(ce.getAttribute(COMPATIBILITY))
					|| ce.getAttribute(COMPATIBILITY) == null) {
				return true;
			}
			if (LEGACYSTEP.equals(ce.getAttribute(COMPATIBILITY))) {
				return true;
			}
			if (LEGACYONLY.equals(ce.getAttribute(COMPATIBILITY))) {
				return false;
			}
			return false;
