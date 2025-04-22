
	private int getStyleOverride(MPartStack pStack) {
		String overrideStr = pStack.getPersistedState().get(
				IPresentationEngine.STYLE_OVERRIDE_KEY);
		if (overrideStr == null || overrideStr.length() == 0)
			return -1;

		int val = -1;
		val = Integer.parseInt(overrideStr);
		return val;
	}

