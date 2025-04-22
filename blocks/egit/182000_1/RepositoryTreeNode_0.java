			Repository repository = getRepository();
			if (repository == null) {
				return null;
			}
			if (repository.isBare()) {
				return repository.getDirectory();
			}
			return repository.getWorkTree();
