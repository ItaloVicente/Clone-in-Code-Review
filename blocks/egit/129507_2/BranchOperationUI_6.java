		Optional<Repository> invalidRepo = Stream.of(repositories) //
				.filter(r -> !r.getRepositoryState().canCheckout()) //
				.findFirst();

		if (invalidRepo.isPresent()) {
			PlatformUI.getWorkbench().getDisplay()
					.asyncExec(() -> showRepositoryInInvalidStateForCheckout(
							invalidRepo.get()));
			return null;
		}

		Collection<Repository> repos = Arrays.asList(repositories);
		if (LaunchFinder.shouldCancelBecauseOfRunningLaunches(repos, monitor)) {
			return null;
