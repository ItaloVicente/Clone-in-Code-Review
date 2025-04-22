
			Repository repo = null;
			for (IResource res : resources) {
				RepositoryMapping map = RepositoryMapping.getMapping(res);
				if (repo == null)
					repo = map.getRepository();
				if (repo != map.getRepository())
					throw new ExecutionException(
							UIText.AbstractHistoryCommanndHandler_NoUniqueRepository);

			}
