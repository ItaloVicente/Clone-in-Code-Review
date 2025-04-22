	private Map<IPath, Collection<String>> getFolderMap(
			IProgressMonitor monitor) {
		SubMonitor progress = SubMonitor.convert(monitor, paths.size());
		Map<IPath, Collection<String>> result = new HashMap<>();
		for (IPath path : paths) {
			if (progress.isCanceled()) {
				return null;
			}
			IPath parent = path.removeLastSegments(1);
			Collection<String> values = result.get(parent);
			if (values == null) {
				values = new LinkedHashSet<>();
				result.put(parent, values);
			}
			values.add(path.lastSegment());
			progress.worked(1);
		}
		return result;
	}

	private Map<IPath, Collection<String>> pruneFolderMap(
			Map<IPath, Collection<String>> perFolder, IProgressMonitor monitor)
			throws IOException {
		SubMonitor progress = SubMonitor.convert(monitor, perFolder.size());
		for (Map.Entry<IPath, Collection<String>> entry : perFolder
				.entrySet()) {
			pruneFolder(entry.getKey(), entry.getValue(), progress.newChild(1));
			if (progress.isCanceled()) {
				return null;
			}
		}
		return perFolder;
	}

	private void pruneFolder(IPath folder, Collection<String> files,
			IProgressMonitor monitor)
			throws IOException {
		if (files.isEmpty()) {
			return;
		}
		Repository repository = Activator.getDefault().getRepositoryCache()
				.getRepository(folder);
		if (repository == null || repository.isBare()) {
			files.clear();
			return;
		}
		WorkingTreeIterator treeIterator = IteratorService
				.createInitialIterator(repository);
		if (treeIterator == null) {
			files.clear();
			return;
		}
		IPath repoRelativePath = folder.makeRelativeTo(
				new Path(repository.getWorkTree().getAbsolutePath()));
		if (repoRelativePath.equals(folder)) {
			files.clear();
			return;
		}
		Collection<String> repoRelative = new HashSet<>(files.size());
		for (String file : files) {
			repoRelative.add(repoRelativePath.append(file).toPortableString());
		}
		files.clear();
		try (TreeWalk walk = new TreeWalk(repository)) {
			walk.addTree(treeIterator);
			walk.setFilter(PathFilterGroup.createFromStrings(repoRelative));
			while (walk.next()) {
				if (monitor.isCanceled()) {
					return;
				}
				WorkingTreeIterator workingTreeIterator = walk.getTree(0,
						WorkingTreeIterator.class);
				if (repoRelative.contains(walk.getPathString())) {
					if (!workingTreeIterator.isEntryIgnored()) {
						files.add(walk.getNameString());
					}
				} else if (workingTreeIterator.getEntryFileMode()
						.equals(FileMode.TREE)) {
					walk.enterSubtree();
				}
			}
		}
	}

	private void updateGitIgnores(Map<IPath, Collection<String>> perFolder,
			IProgressMonitor monitor) throws CoreException, IOException {
		SubMonitor progress = SubMonitor.convert(monitor, perFolder.size() * 2);
		for (Map.Entry<IPath, Collection<String>> entry : perFolder
				.entrySet()) {
			if (progress.isCanceled()) {
				return;
			}
			IContainer container = ResourceUtil
					.getContainerForLocation(entry.getKey(), false);
			if (container instanceof IWorkspaceRoot) {
				container = null;
			}
			Collection<String> files = entry.getValue();
			if (files.isEmpty()) {
				progress.worked(1);
				continue;
			}
			StringBuilder builder = new StringBuilder();
			for (String file : files) {
				builder.append('/').append(file);
				boolean isDirectory = false;
				IResource resource = container != null
						? container.findMember(file) : null;
				if (resource != null) {
					isDirectory = resource.getType() != IResource.FILE;
				} else {
					isDirectory = entry.getKey().append(file).toFile()
							.isDirectory();
				}
				if (isDirectory) {
					builder.append('/');
				}
				builder.append('\n');
			}
			progress.worked(1);
			if (progress.isCanceled()) {
				return;
			}
			addToGitIgnore(container, entry.getKey(), builder.toString(),
					progress.newChild(1));
		}
	}

	private void addToGitIgnore(IContainer container, IPath parent,
			String entry, IProgressMonitor monitor)
			throws CoreException, IOException {
		SubMonitor progress = SubMonitor.convert(monitor, 1);
		if (container == null) {
