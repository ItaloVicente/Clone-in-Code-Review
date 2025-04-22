	private void loadPreviousCommit() {
		try {
			ObjectId parentId = repo.resolve(Constants.HEAD);
			if (parentId != null)
				previousCommit = new RevWalk(repo).parseCommit(parentId);
		} catch (IOException e) {
			Activator.handleError(UIText.CommitAction_errorRetrievingCommit, e,
					true);
		}
	}

