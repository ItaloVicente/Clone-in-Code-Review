
	public int getStyleOverride(MUIElement mElement) {
		String overrideStr = mElement.getPersistedState().get(
				IPresentationEngine.STYLE_OVERRIDE_KEY);
		if (overrideStr == null || overrideStr.length() == 0)
			return -1;

		int val = -1;
		val = Integer.parseInt(overrideStr);
		return val;
	}
