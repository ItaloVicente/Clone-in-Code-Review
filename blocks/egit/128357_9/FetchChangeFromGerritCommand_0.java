			ElementListSelectionDialog repositorySelectionDialog = new ElementListSelectionDialog(
					shell, new NoRepositorySelectedLabelProvider());
			repositorySelectionDialog.setElements(repositories);

			repositorySelectionDialog.setTitle(
					UIText.FetchChangeFromGerritCommand_noRepositorySelectedTitle);

			if (repositorySelectionDialog.open() != Window.OK) {
				return null;
			} else {
				repository = (Repository) repositorySelectionDialog
						.getResult()[0]; // Not the best solution I suppose. Any
			}
