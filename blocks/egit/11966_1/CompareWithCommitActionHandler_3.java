		try {
			CompareUtils.compare(resources, repo, Constants.HEAD, commitId,
					true);
		} catch (IOException e) {
			Activator.handleError(
					UIText.CompareWithRefAction_errorOnSynchronize, e, true);
