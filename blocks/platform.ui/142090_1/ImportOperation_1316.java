		}
	}

	boolean queryOverwrite(IPath resourcePath)
			throws OperationCanceledException {
		String overwriteAnswer = overwriteCallback.queryOverwrite(resourcePath
				.makeRelative().toString());

		if (overwriteAnswer.equals(IOverwriteQuery.CANCEL)) {
