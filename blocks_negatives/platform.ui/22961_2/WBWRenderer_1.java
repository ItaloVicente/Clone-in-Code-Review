	/**
	 * @param wbwModel
	 * @return Returns the style override bits or -1 if there is no override
	 */
	private int getStyleOverride(MWindow wbwModel) {
		String overrideStr = wbwModel.getPersistedState().get(
				IPresentationEngine.STYLE_OVERRIDE_KEY);
		if (overrideStr == null || overrideStr.length() == 0)
			return -1;

		int val = -1;
		val = Integer.parseInt(overrideStr);
		return val;
	}

