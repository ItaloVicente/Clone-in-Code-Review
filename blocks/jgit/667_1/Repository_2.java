		if (new File(gitDir
			try {
				if (!DirCache.read(this).hasUnmergedPaths()) {
					return RepositoryState.MERGING_RESOLVED;
				}
			} catch (IOException e) {
			}
