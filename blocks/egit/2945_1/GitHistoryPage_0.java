	private boolean repoHasBeenRemoved(final Repository repo) {
		return (repo != null && repo.getDirectory() != null && !repo
				.getDirectory().exists());
	}

	private void clearHistoryPage() {
		currentRepo = null;
		name = ""; //$NON-NLS-1$
		input = null;
		commentViewer.setInput(null);
		fileViewer.setInput(null);
		setInput(null);
	}

