	private ICompareInput prepareCompareInput(Repository repository,
			List<String> filterPaths, IProgressMonitor monitor)
			throws InvocationTargetException, InterruptedException {
		try {
			final GitResourceVariantTreeProvider variantTreeProvider = new DirCacheResourceVariantTreeProvider(
					repository, useWorkspace);
			final Subscriber subscriber = new GitResourceVariantTreeSubscriber(
					variantTreeProvider);
			checkCanceled(monitor);

			final Set<IProject> projects = new LinkedHashSet<IProject>();
			for (IResource root : subscriber.roots())
				projects.add(root.getProject());

			final Set<IResource> resourcesInOperation = new LinkedHashSet<IResource>();
			boolean outOfWS = false;
			for (IPath path : locations) {
				boolean foundMatchInWS = false;
				final Iterator<IProject> projectIterator = projects.iterator();
				while (!foundMatchInWS && projectIterator.hasNext()) {
					final IProject project = projectIterator.next();
					if (project.getLocation().isPrefixOf(path)) {
						final IPath projectRelativePath = path
								.removeFirstSegments(project.getLocation()
										.segmentCount());
						resourcesInOperation.add(project
								.getFile(projectRelativePath));
						foundMatchInWS = true;
					}
				}
				if (!foundMatchInWS) {
					if (!resourcesInOperation.isEmpty())
						break;
					else
						outOfWS = true;
				} else if (outOfWS)
					break;
