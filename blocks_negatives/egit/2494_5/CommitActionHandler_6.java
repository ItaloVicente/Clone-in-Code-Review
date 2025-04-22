		for (IFile file : files) {
			for (IResource resource : selectedResources) {
				if (resource.contains(file)) {
					preselectionCandidates.add(file);
					break;
				}
			}
		}
		return preselectionCandidates;
	}

	private void loadPreviousCommit(ExecutionEvent event)
			throws ExecutionException {
		IProject project = getProjectsForSelectedResources(event)[0];

		Repository repo = RepositoryMapping.getMapping(project).getRepository();
		try {
			ObjectId parentId = repo.resolve(Constants.HEAD);
			if (parentId != null)
				previousCommit = new RevWalk(repo).parseCommit(parentId);
		} catch (IOException e) {
			Activator.handleError(UIText.CommitAction_errorRetrievingCommit, e,
					true);
		}
	}

	private void buildIndexHeadDiffList(IProject[] selectedProjects, IProgressMonitor monitor)
			throws IOException, OperationCanceledException {
		HashMap<Repository, HashSet<IProject>> repositories = new HashMap<Repository, HashSet<IProject>>();

		for (IProject project : selectedProjects) {
			RepositoryMapping repositoryMapping = RepositoryMapping
					.getMapping(project);
			assert repositoryMapping != null;

			Repository repository = repositoryMapping.getRepository();

			HashSet<IProject> projects = repositories.get(repository);

			if (projects == null) {
				projects = new HashSet<IProject>();
				repositories.put(repository, projects);
			}

			projects.add(project);
		}

		monitor.beginTask(UIText.CommitActionHandler_calculatingChanges,
				repositories.size() * 1000);
		for (Map.Entry<Repository, HashSet<IProject>> entry : repositories
				.entrySet()) {
			Repository repository = entry.getKey();
			EclipseGitProgressTransformer jgitMonitor = new EclipseGitProgressTransformer(monitor);
			HashSet<IProject> projects = entry.getValue();
			CountingVisitor counter = new CountingVisitor();
			for (IProject p : projects) {
				try {
					p.accept(counter);
				} catch (CoreException e) {
				}
			}
			IndexDiff indexDiff = new IndexDiff(repository, Constants.HEAD,
					IteratorService.createInitialIterator(repository));
			indexDiff.diff(jgitMonitor, counter.count, 0, NLS.bind(
					UIText.CommitActionHandler_repository, repository
							.getDirectory().getPath()));
			indexDiffs.put(repository, indexDiff);

			for (IProject project : projects) {
				includeList(project, indexDiff.getAdded(), indexChanges);
				includeList(project, indexDiff.getChanged(), indexChanges);
				includeList(project, indexDiff.getRemoved(), indexChanges);
				includeList(project, indexDiff.getMissing(), notIndexed);
				includeList(project, indexDiff.getModified(), notIndexed);
				includeList(project, indexDiff.getUntracked(), notTracked);
			}
			if (monitor.isCanceled())
				throw new OperationCanceledException();
		}
		monitor.done();
	}

	static class CountingVisitor implements IResourceVisitor {
		int count;
		public boolean visit(IResource resource) throws CoreException {
			count++;
			return true;
		}
	}

	private void includeList(IProject project, Set<String> added,
			Set<IFile> category) {
		String repoRelativePath = RepositoryMapping.getMapping(project)
				.getRepoRelativePath(project);
		if (repoRelativePath.length() > 0) {
		}

		for (String filename : added) {
			try {
				if (!filename.startsWith(repoRelativePath))
					continue;
				String projectRelativePath = filename
						.substring(repoRelativePath.length());
				IFile member = project.getFile(projectRelativePath);
				if (!files.contains(member))
					files.add(member);
				category.add(member);
			} catch (Exception e) {
				if (GitTraceLocation.UI.isActive())
					GitTraceLocation.getTrace().trace(
							GitTraceLocation.UI.getLocation(), e.getMessage(),
							e);
				continue;
		}
