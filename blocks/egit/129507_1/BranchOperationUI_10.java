	private void show(final @NonNull Map<Repository, CheckoutResult> results) {

		if (allBranchOperationsSucceeded(results)) {

			if (results.entrySet().stream() //
					.map(Map.Entry::getKey) //
					.anyMatch(RepositoryUtil::isDetachedHead))
				showDetachedHeadWarning();
			return;
		}

		if (this.isSingleRepositoryOperation) {
			Repository repo = repositories[0];
			CheckoutResult result = results.get(repo);
			handleSingleRepositoryCheckoutOperationResult(repo,
					result);
		}

		handleMultipleRepositoryCheckoutError(results);
	}

	private boolean allBranchOperationsSucceeded(
			final @NonNull Map<Repository, CheckoutResult> results)
	{
		return results.entrySet().stream() //
				.map(Map.Entry::getValue) //
				.allMatch(r -> r.getStatus() == CheckoutResult.Status.OK);
	}

	private void handleSingleRepositoryCheckoutOperationResult(Repository repository,
			CheckoutResult result) {

