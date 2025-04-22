
	@Nullable
	static Repository getRepository(@NonNull IStructuredSelection selection) {

		IPath[] locations = SelectionUtils.getSelectedLocations(selection);
		if (GitTraceLocation.SELECTION.isActive())
			GitTraceLocation.getTrace().trace(
					GitTraceLocation.SELECTION.getLocation(), "selection=" //$NON-NLS-1$
					+ selection + ", locations=" //$NON-NLS-1$
					+ Arrays.toString(locations));
		boolean hadNull = false;
		Repository result = null;
		for (IPath location : locations) {
			RepositoryMapping mapping = RepositoryMapping.getMapping(location);
			Repository repo;
			if (mapping != null) {
				repo = mapping.getRepository();
			} else {
				repo = org.eclipse.egit.core.Activator.getDefault()
						.getRepositoryCache().getRepository(location);
			}
			if (repo == null) {
				hadNull = true;
			}
			if (result == null) {
				result = repo;
			}
			boolean mismatch = hadNull && result != null;
			if (mismatch || result != repo) {
				return null;
			}
		}

		if (result == null) {
			for (Object o : selection.toArray()) {
				Repository nextRepo = AdapterUtils.adapt(o, Repository.class);
				if (nextRepo != null && result != null && result != nextRepo) {
					return null;
				}
				result = nextRepo;
			}
		}
		return result;
	}
