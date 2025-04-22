		String fullBranchName = getFullBranchName(newText);
		IStatus status = Utils.validateNewRefName(fullBranchName,
				repository.getRepository(), "", //$NON-NLS-1$
				false);

		if (status.isOK()) {
