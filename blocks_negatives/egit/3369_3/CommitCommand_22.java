		if (RepositoryMapping.findRepositoryMapping(repository) == null) {
			MessageDialog.openInformation(getShell(event),
					UIText.CommitCommand_committingNotPossible,
					UIText.CommitCommand_noProjectsImported);
			return null;
		}
