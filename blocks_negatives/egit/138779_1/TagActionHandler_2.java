		try {
			tagTarget = getTagTarget(repo, dialog.getTagCommit());
		} catch (IOException e1) {
			Activator.handleError(UIText.TagAction_unableToResolveHeadObjectId,
					e1, true);
			return null;
