	private void discardChanges(Repository repository, Collection<String> paths)
			throws GitAPIException {
		ResourceUtil.saveLocalHistory(repository);
		CheckoutCommand checkoutCommand = new Git(repository).checkout();
		if (revision != null)
			checkoutCommand.setStartPoint(revision);
		if (stage != null)
			checkoutCommand.setStage(stage.checkoutStage);
		if (paths.isEmpty() || paths.contains("")) //$NON-NLS-1$
			checkoutCommand.setAllPaths(true);
		else {
			for (String path : paths)
				checkoutCommand.addPath(path);
