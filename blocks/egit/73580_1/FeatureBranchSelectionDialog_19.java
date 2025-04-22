		List<Ref> selection = filteredFeatures.getSelection();
		if (selection.isEmpty() || selection.get(0) == null) {
			getButton(OK).setEnabled(false);
			return;
		}
		Repository repository = gfRepo.getRepository();
		try {
			Ref currentBranch = repository.exactRef(repository.getFullBranch());
			getButton(OK).setEnabled(!selection.get(0).equals(currentBranch));
		} catch (IOException e) {
			Activator.logError("Unable to find current branch", e); //$NON-NLS-1$
		}
