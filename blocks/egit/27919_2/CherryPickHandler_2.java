		try {
			if (!UIRepositoryUtils.handleUncommittedFiles(repo, parent))
				return null;
		} catch (GitAPIException e) {
			Activator.logError(e.getMessage(), e);
			return null;
		}
