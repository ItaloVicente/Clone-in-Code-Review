		if (repository == null) {
			Shell shell = getShell(event);
			MessageDialog
					.openInformation(
							shell,
							UIText.FetchChangeFromGerritCommand_noRepositorySelectedTitle,
							UIText.FetchChangeFromGerritCommand_noRepositorySelectedMessage);

			return null;
		}

