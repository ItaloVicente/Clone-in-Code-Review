		try {
			resetCommand.call();
			monitor.worked(1);
		} catch (Exception e) {
			Activator.logError(e.getMessage(), e);
		} finally {
			monitor.done();
			findRepositoryMapping(repo).fireRepositoryChanged();
