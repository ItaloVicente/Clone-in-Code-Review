		try {
			git.gc().setAggressive(aggressive)
					.setPreserveOldPacks(preserveOldPacks)
					.setPrunePreserved(prunePreserved)
					.setProgressMonitor(new TextProgressMonitor(errw)).call();
		} catch (GitAPIException e) {
			throw die(e.getMessage()
		}
