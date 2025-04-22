		if (target == null) {
			return null;
		}

		Optional<Repository> invalidRepo = Stream.of(repositories) //
				.filter(r -> !r.getRepositoryState().canCheckout()) //
				.findFirst();

		if (invalidRepo.isPresent()) {
			PlatformUI.getWorkbench().getDisplay()
					.asyncExec(() -> showRepositoryInInvalidStateForCheckout(
							invalidRepo.get()));
			return null;
		}
