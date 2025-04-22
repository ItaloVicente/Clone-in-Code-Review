	}

	private int rank(ModifierKey modifierKey) {

		if (Util.isWindows()) {
			return rankWindows(modifierKey);
		}

		if (Util.isGtk()) {
			return rankGNOME(modifierKey);
		}

		if (Util.isMac()) {
			return rankMacOSX(modifierKey);
		}

		return UNKNOWN_KEY;
	}

	private final int rankGNOME(ModifierKey modifierKey) {
		if (ModifierKey.SHIFT.equals(modifierKey)) {
			return 0;
		}

		if (ModifierKey.CTRL.equals(modifierKey)) {
			return 1;
		}

		if (ModifierKey.ALT.equals(modifierKey)) {
			return 2;
		}

		return UNKNOWN_KEY;

	}

