	private void scanDeltaAndRefresh(
			GitResourceVariantTreeSubscriber subscriber,
			RepositoryMapping mapping, IResourceDelta delta) {
		Repository repo = mapping.getRepository();
		GitResourceDeltaVisitor visitor = new GitResourceDeltaVisitor(repo);
		try {
			delta.accept(visitor);
			Collection<IFile> files = visitor.getFileResourcesToUpdate();
			if (files != null && files.isEmpty())
