		try (InputStream in = inputStream.load()) {
			try {
				command = FilterCommandRegistry.createFilterCommand(
						checkoutMetadata.smudgeFilterCommand
						channel);
			} catch (IOException e) {
				LOG.error(JGitText.get().failedToDetermineFilterDefinition
				if (!isMandatory) {
					try (InputStream again = inputStream.load()) {
						again.transferTo(channel);
					}
				} else {
					throw e;
				}
