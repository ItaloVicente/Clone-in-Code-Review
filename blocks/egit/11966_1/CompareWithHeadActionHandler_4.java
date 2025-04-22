		try {
			CompareUtils.compare(resources, repository, Constants.HEAD,
					Constants.HEAD, true);
		} catch (IOException e) {
			Activator.handleError(
					UIText.CompareWithRefAction_errorOnSynchronize, e, true);
