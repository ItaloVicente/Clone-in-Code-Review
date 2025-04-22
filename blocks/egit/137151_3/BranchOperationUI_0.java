	private void removeRepositoriesAlreadySwitched() {
		repositories = Stream.of(repositories)
				.filter(r -> !target.equals(getBranch(r)))
				.toArray(Repository[]::new);
		this.isSingleRepositoryOperation = repositories.length == 1;
		if (!isSingleRepositoryOperation) {
			showQuestionsBeforeCheckout = false;
		}
	}

	private String getBranch(Repository repo) {
		try {
			return repo.getBranch();
		} catch (IOException e) {
			return ""; //$NON-NLS-1$
		}
	}

