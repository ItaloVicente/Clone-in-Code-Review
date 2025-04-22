	private void advanceOrCancel(ProgressMonitor pm) throws CanceledException {
		if (pm.isCancelled()) {
			throw new CanceledException(JGitText.get().renameCancelled);
		}
		pm.update(1);
	}

