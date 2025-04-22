	private void discardChanges() throws GitAPIException {
		Map<Repository, Collection<String>> pathsByRepository = ResourceUtil
				.splitResourcesByRepository(files);
		for (Repository repository : pathsByRepository.keySet()) {
			Collection<String> paths = pathsByRepository.get(repository);
			CheckoutCommand checkoutCommand = new Git(repository).checkout();
			checkoutCommand.setStartPoint(this.revision);
			for (String path : paths)
				checkoutCommand.addPath(path);
			checkoutCommand.call();
