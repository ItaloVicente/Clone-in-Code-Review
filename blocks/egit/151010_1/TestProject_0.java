	private void deleteProject() throws CoreException {
		int trials = 3;
		while (trials-- > 0) {
			try {
				project.delete(true, null);
				trials = 0;
			} catch (CoreException e) {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException iex) {
					Thread.currentThread().interrupt();
				}
				if (trials <= 0) {
					throw e;
				}
			}
		}
	}

