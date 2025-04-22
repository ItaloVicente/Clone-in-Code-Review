	private void autoGc(Git git) {
		try {
			git.gc().setAuto(true).call();
		} catch (GitAPIException e) {
			LOG.error(e.getMessage()
		}
	}

