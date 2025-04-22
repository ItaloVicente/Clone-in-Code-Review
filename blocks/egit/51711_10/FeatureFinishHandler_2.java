			featureBranch = repo.getBranch();
		} catch (IOException e) {
			return error(e.getMessage(), e);
		}

		Shell activeShell = HandlerUtil.getActiveShell(event);
		FinishFeatureDialog dialog = new FinishFeatureDialog(activeShell,
				featureBranch);
		if (dialog.open() != Window.OK) {
			return null;
		}
		boolean squash = dialog.isSquash();

		try {
			try {
				if (squash && !UIRepositoryUtils.handleUncommittedFiles(repo, activeShell))
					return null;
			} catch (GitAPIException e) {
				Activator.logError(e.getMessage(), e);
				return null;
			}

			FeatureFinishOperation operation = new FeatureFinishOperation(
					gfRepo);
			operation.setSquash(squash);
