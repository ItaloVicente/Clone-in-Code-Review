	@NonNull
	public CleanupMode getCleanupMode() {
		return cleanupMode;
	}

	@NonNull
	public CleanupMode resolve(@NonNull CleanupMode mode
			boolean defaultStrip) {
		if (CleanupMode.DEFAULT == mode) {
			CleanupMode defaultMode = getCleanupMode();
			if (CleanupMode.DEFAULT == defaultMode) {
				return defaultStrip ? CleanupMode.STRIP
						: CleanupMode.WHITESPACE;
			}
			return defaultMode;
		}
		return mode;
	}

