		if (text == null) {
			return ""; //$NON-NLS-1$
		}
		CleanupMode mode = cleanupMode;
		if (mode != null) {
			text = CommitConfig.cleanText(text, mode, '#');
		}
