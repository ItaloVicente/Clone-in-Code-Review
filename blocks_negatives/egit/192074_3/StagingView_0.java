	private void setCleanup(Repository repo, boolean redraw) {
		if (repo != null) {
			CommitConfig config = repo.getConfig().get(CommitConfig.KEY);
			CleanupMode mode = config.resolve(CleanupMode.DEFAULT, true);
			commitMessageText.setCleanupMode(mode, '#');
		} else {
			commitMessageText.setCleanupMode(CleanupMode.STRIP, '#');
		}
		if (redraw) {
			commitMessageText.invalidatePresentation();
		}
	}

