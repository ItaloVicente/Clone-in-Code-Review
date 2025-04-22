		Map<String
		environment.put(Constants.GIT_DIR_KEY
				repository.getDirectory().getAbsolutePath());
		if (!repository.isBare()) {
			environment.put(Constants.GIT_WORK_TREE_KEY
					repository.getWorkTree().getAbsolutePath());
		}
