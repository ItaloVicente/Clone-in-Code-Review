		CleanupMode cleanup = mode;
		if (cleanup == null || CleanupMode.DEFAULT.equals(cleanup)) {
			cleanup = CleanupMode.STRIP;
		}
		messageArea.setCleanupMode(cleanup, commentChar);
		messageArea.setText(commitMessage);
