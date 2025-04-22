			RevCommit ancestorCommit = getCommonAncestor(rw, rightCommit,
					headCommit);

			checkCanceled(monitor);

			setLabels(repository, rightCommit, headCommit, ancestorCommit);

			final ICompareInput input = prepareCompareInput(repository,
					filterPaths, monitor);
			if (input != null)
				return input;

			checkCanceled(monitor);
			return buildDiffContainer(repository, headCommit, ancestorCommit,
					filterPaths, rw, monitor);
		} catch (IOException e) {
			throw new InvocationTargetException(e);
		} finally {
			if (rw != null)
				rw.dispose();
			monitor.done();
		}
	}

	private void checkCanceled(IProgressMonitor monitor)
			throws InterruptedException {
		if (monitor.isCanceled())
			throw new InterruptedException();
	}

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
					final IPath projectLocation = project.getLocation();
					if (projectLocation.equals(path)) {
						resourcesInOperation
								.addAll(getConflictingFilesFrom(project));
						foundMatchInWS = true;
					} else if (project.getLocation().isPrefixOf(path)) {
						final IResource resource = ResourceUtil
								.getResourceForLocation(path);
						if (resource instanceof IContainer)
							resourcesInOperation
									.addAll(getConflictingFilesFrom((IContainer) resource));
						else
							resourcesInOperation.add(resource);
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
