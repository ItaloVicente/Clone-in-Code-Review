		if (new File(getDirectory()
			try {
				if (!readDirCache().hasUnmergedPaths()) {
					return RepositoryState.REVERTING_RESOLVED;
				}
			} catch (IOException e) {
			}

			return RepositoryState.REVERTING;
		}

