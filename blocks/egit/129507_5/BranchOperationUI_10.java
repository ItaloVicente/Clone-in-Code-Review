	private void show(final @NonNull Map<Repository, CheckoutResult> results) {

		if (allBranchOperationsSucceeded(results)) {

			if (anyRepositoryIsInDetachedHeadState(results)) {
				showDetachedHeadWarning();
			}
			return;
		}

		if (this.isSingleRepositoryOperation) {
			Repository repo = repositories[0];
			CheckoutResult result = results.get(repo);
			handleSingleRepositoryCheckoutOperationResult(repo,
					result);
			return;
		}

		handleMultipleRepositoryCheckoutError(results);
	}

	private boolean allBranchOperationsSucceeded(
			final @NonNull Map<Repository, CheckoutResult> results)
	{
		return results.values().stream()
				.allMatch(r -> r.getStatus() == CheckoutResult.Status.OK);
	}

	private boolean anyRepositoryIsInDetachedHeadState(
			final @NonNull Map<Repository, CheckoutResult> results) {
		return results.keySet().stream()
				.anyMatch(RepositoryUtil::isDetachedHead);
	}

	private void handleSingleRepositoryCheckoutOperationResult(Repository repository,
			CheckoutResult result) {

