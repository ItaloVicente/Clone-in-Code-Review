		if (new File(getDirectory()
			try {
				if (!readDirCache().hasUnmergedPaths()) {
					return RepositoryState.CHERRY_PICKING_RESOLVED;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			return RepositoryState.CHERRY_PICKING;
		}

