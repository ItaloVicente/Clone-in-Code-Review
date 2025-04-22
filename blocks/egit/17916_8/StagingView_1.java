	public void setAmending(boolean isAmending) {
		if (amendPreviousCommitAction.isChecked() != isAmending) {
			amendPreviousCommitAction.setChecked(isAmending);
			amendPreviousCommitAction.run();
		}

	}

	public void reload(final Repository repository) {
