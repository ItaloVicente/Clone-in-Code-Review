		if (!indexDiffAvailable)
			return; // only try to restore the stored repo commit message if

		CommitHelper helper = new CommitHelper(currentRepository);
		CommitMessageComponentState oldState = null;
		if (repositoryChanged
				|| commitMessageComponent.getRepository() != currentRepository) {
