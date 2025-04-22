			Repository repository = node.getRepository();
			repository.addRepositoryChangedListener(listener);
			try {
				repository.scanForRepoChanges();
			} catch (IOException e1) {
			} finally {
				repository.removeRepositoryChangedListener(listener);
