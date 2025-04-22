		Repository newRepository;
		if (selection == null) {
			newRepository = null;
		} else {
			newRepository = SelectionUtils.getRepository(
					SelectionUtils.getStructuredSelection(selection));
		}
		if (repository != newRepository) {
			repository = newRepository;
			fireSourceChanged(ISources.ACTIVE_WORKBENCH_WINDOW,
					REPOSITORY_PROPERTY,
					repository);
