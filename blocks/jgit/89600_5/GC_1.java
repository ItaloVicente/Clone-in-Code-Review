	private void checkCancelled() throws CancelledException {
		if (pm.isCancelled()) {
			throw new CancelledException(JGitText.get().operationCanceled);
		}
	}

