	private int getCharWidth() {
		GC gc = new GC(getTextWidget());
		int charWidth = gc.getFontMetrics().getAverageCharWidth();
		gc.dispose();
		return charWidth;
	}

	private int getLineHeight() {
		return getTextWidget().getLineHeight();
	}

	private static boolean shouldHardWrap() {
		return Activator.getDefault().getPreferenceStore()
				.getBoolean(UIPreferences.COMMIT_DIALOG_HARD_WRAP_MESSAGE);
	}

