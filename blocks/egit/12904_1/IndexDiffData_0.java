		diffs = Collections.unmodifiableMap(diffs2);
		computeRenames(monitor, repo);
	}

	private void computeRenames(IProgressMonitor monitor, Repository repo) {
		monitor.beginTask("Rename computations", diffs.size()); //$NON-NLS-1$
		RenameDetector renameDetector = new RenameDetector(repo);
		renameDetector.addAll(diffs.values());
		try {
			List<DiffEntry> renameList = renameDetector
					.compute(new EclipseGitProgressTransformer(monitor));
			for (DiffEntry e : renameList) {
				if (e.getNewPath() != null)
					renames.put(e.getNewPath(), e);
				else
					renames.put(e.getOldPath(), e);
			}
		} catch (IOException e) {
			Activator.logError("Rename detection failed", e); //$NON-NLS-1$
		}
		monitor.done();
