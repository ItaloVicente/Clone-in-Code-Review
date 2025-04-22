		if (engine != null) {
			boolean mruControlledByCSS = prefs.getBoolean(StackRenderer.MRU_CONTROLLED_BY_CSS_KEY, false);
			if (mruControlledByCSS) {
				return;
			}
		}
