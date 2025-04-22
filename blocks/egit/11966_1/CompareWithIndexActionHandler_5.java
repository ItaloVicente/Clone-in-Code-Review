		final IResource[] resources = getSelectedResources(event);

		try {
			CompareUtils.compare(resources, repository, Constants.HEAD,
					GitFileRevision.INDEX, true);
		} catch (IOException e) {
			Activator.handleError(
					UIText.CompareWithRefAction_errorOnSynchronize, e, true);
