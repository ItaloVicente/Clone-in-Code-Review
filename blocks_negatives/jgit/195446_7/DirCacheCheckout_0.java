		try {
			command = FilterCommandRegistry.createFilterCommand(
					checkoutMetadata.smudgeFilterCommand, repo, ol.openStream(),
					channel);
		} catch (IOException e) {
			LOG.error(JGitText.get().failedToDetermineFilterDefinition, e);
			if (!isMandatory) {
				ol.copyTo(channel);
			} else {
				throw e;
