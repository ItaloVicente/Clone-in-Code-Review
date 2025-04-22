	private final int rankMacOSX(ModifierKey modifierKey) {
		if (ModifierKey.SHIFT.equals(modifierKey)) {
			return 0;
		}

		if (ModifierKey.CTRL.equals(modifierKey)) {
			return 1;
		}

		if (ModifierKey.ALT.equals(modifierKey)) {
			return 2;
		}

		if (ModifierKey.COMMAND.equals(modifierKey)) {
			return 3;
		}

		return UNKNOWN_KEY;
	}

	private final int rankWindows(ModifierKey modifierKey) {
		if (ModifierKey.CTRL.equals(modifierKey)) {
			return 0;
		}

		if (ModifierKey.ALT.equals(modifierKey)) {
			return 1;
		}

		if (ModifierKey.SHIFT.equals(modifierKey)) {
			return 2;
		}

		return UNKNOWN_KEY;
	}
