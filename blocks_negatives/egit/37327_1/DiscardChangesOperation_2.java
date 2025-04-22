	private void discardChanges() throws GitAPIException {
		Map<Repository, Collection<String>> pathsByRepository = ResourceUtil
				.splitResourcesByRepository(files);
		for (Entry<Repository, Collection<String>> entry : pathsByRepository.entrySet()) {
			Repository repository = entry.getKey();
			ResourceUtil.saveLocalHistory(repository);
			Collection<String> paths = entry.getValue();
			CheckoutCommand checkoutCommand = new Git(repository).checkout();
			checkoutCommand.setStartPoint(this.revision);
				checkoutCommand.setAllPaths(true);
			else
				for (String path : paths)
					checkoutCommand.addPath(path);
			checkoutCommand.call();
