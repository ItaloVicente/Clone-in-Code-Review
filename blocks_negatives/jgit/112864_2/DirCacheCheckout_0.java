		FilterCommand command = null;
		try {
			command = FilterCommandRegistry.createFilterCommand(
					checkoutMetadata.smudgeFilterCommand, repo, ol.openStream(),
					channel);
		} catch (IOException e) {
			LOG.error(JGitText.get().failedToDetermineFilterDefinition, e);
			ol.copyTo(channel);
		}
