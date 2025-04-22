			if (resources.length == 1) {
				view.showHistoryFor(resources[0]);
				return null;
			}
			Repository repo = null;
			for (IResource res : resources) {
				Repository actRepo = RepositoryMapping.getMapping(res)
						.getRepository();
				if (repo == null)
					repo = actRepo;
				if (repo != actRepo)
					throw new ExecutionException(
							UIText.AbstractHistoryCommanndHandler_NoUniqueRepository);
			}
			HistoryPageInput input = new HistoryPageInput(repo, resources);
			view.showHistoryFor(input);
