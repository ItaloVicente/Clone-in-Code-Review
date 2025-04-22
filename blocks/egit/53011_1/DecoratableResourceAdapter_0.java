		}
		if (repository.isBare()) {
			return null;
		}
		File workTree = repository.getWorkTree();
		return stripWorkDir(workTree, location.toFile());
