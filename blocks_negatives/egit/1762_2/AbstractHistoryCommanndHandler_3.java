		Repository repo = null;
		if (input instanceof ResourceList) {
			for (IResource res : ((ResourceList) input).getItems()) {
				Repository resourceRepo = RepositoryMapping.getMapping(res)
						.getRepository();
				if (repo == null)
					repo = resourceRepo;
				if (repo != resourceRepo)
					throw new ExecutionException(
							UIText.AbstractHistoryCommanndHandler_NoUniqueRepository);
			}
			return repo;
