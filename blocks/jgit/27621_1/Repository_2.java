		try {
			if (readDirCache().hasUnmergedPaths()) {
				return RepositoryState.CHERRY_PICKING;
			}
		} catch (IOException e) {
		}

