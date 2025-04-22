		final IFile[] files = commitDialog.getSelectedFiles();
		ISchedulingRule rule = MultiRule.combine(files);

		final Commit prevCommit = previousCommit;
		final Map<Repository, List<IFile>> notIndexedFiles = new HashMap<Repository, List<IFile>>(
				notIndexed);
		final Map<Repository, List<IFile>> notTrackedFiles = new HashMap<Repository, List<IFile>>(
				notTracked);

		Job job = new Job("Committing resources...") {
			@Override
			protected IStatus run(IProgressMonitor monitor) {
				SubMonitor subMonitor = SubMonitor.convert(monitor);
				Map<RepositoryMapping, List<IFile>> targets = new HashMap<RepositoryMapping, List<IFile>>();
				for (IFile file : files) {
					RepositoryMapping mapping = RepositoryMapping
							.getMapping(file);
					List<IFile> list = targets.get(mapping);
					if (list == null) {
						list = new ArrayList<IFile>();
						targets.put(mapping, list);
					}
					list.add(file);
