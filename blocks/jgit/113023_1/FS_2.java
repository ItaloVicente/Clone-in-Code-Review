		private boolean waitForProcessCompletion() {
			try {
				return p.waitFor(PROCESS_EXIT_TIMEOUT
			} catch (InterruptedException e) {
				LOG.error("Current thread interrupted while running '" + desc + "'"
			}
			return false;
		}

		private void setError(IOException e
