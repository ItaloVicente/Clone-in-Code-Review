		try {
			addCommand.call();
		} catch (GitAPIException e) {
			Activator.logError(UIText.AddToIndexCommand_addingFilesFailed,
					e);
		}
