		try {
			initAndStartRevWalk(true);
		} catch (IllegalStateException e) {
			Activator.handleError(e.getMessage(), e.getCause(), true);
			return false;
		}

		return true;
	}

	private ArrayList<String> buildFilterPaths(final IResource[] inResources,
			final File[] inFiles, final Repository db)
			throws IllegalStateException {
