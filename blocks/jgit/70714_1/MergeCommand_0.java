	private void autoGc() {
		try (Git git = Git.wrap(repo)) {
			git.gc().setAuto(true).call();
		} catch (GitAPIException e) {
			LOG.error(e.getMessage()
		}
	}

