	private void checkCancelled() throws IOException {
		if (pm.isCancelled()) {
			throw new IOException(JGitText.get().operationCanceled);
		}
	}

