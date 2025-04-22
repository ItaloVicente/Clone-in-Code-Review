				for (Repository repository : repositories) {

					CheckoutResult result = checkoutRepository(repository,
							progress);

					if (result.getStatus() == Status.NONDELETED) {
						retryDelete(repository, result.getUndeletedList());
					}

					results.put(repository, result);
				}
				refreshAffectedProjects(progress);
			}

			public CheckoutResult checkoutRepository(Repository repo,
					SubMonitor progress) throws CoreException {

				closeProjectsMissingAfterCheckout(repo, progress);

				try (Git git = new Git(repo)) {
