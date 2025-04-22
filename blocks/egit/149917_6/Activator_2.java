	public void waitForRefresh(Repository repo) {
		try {
			while (refreshJob.waitsForRefresh(repo)) {
				refreshJob.join();
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	public void waitForRefresh(Collection<Repository> repos) {
		for (Repository r : repos) {
			waitForRefresh(r);
		}
	}

