			if (resources.length == 1) {
				view.showHistoryFor(resources[0]);
				return null;
			}

			Repository repo = null;
			for (IResource res : resources) {
				RepositoryMapping map = RepositoryMapping.getMapping(res);
				if (repo == null)
					repo = map.getRepository();
				if (repo != map.getRepository())
					throw new ExecutionException(
							UIText.AbstractHistoryCommanndHandler_NoUniqueRepository);

			}
			HistoryPageInput list = new HistoryPageInput(repo, resources);
