	private Collection<IFile> getSelectedFiles() {
		List<IFile> preselectionCandidates = new ArrayList<IFile>();
		IResource[] selectedResources = getSelectedResources();
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

	private void loadPreviousCommit() {
		IProject project = getProjectsForSelectedResources()[0];

		Repository repo = RepositoryMapping.getMapping(project).getRepository();
		try {
			ObjectId parentId = repo.resolve(Constants.HEAD);
			if (parentId != null)
				previousCommit = repo.mapCommit(parentId);
		} catch (IOException e) {
			Utils.handleError(getTargetPart().getSite().getShell(), e, UIText.CommitAction_errorDuringCommit, UIText.CommitAction_errorRetrievingCommit);
		}
	}

	private void buildIndexHeadDiffList() throws IOException, CoreException {
		HashMap<Repository, HashSet<IProject>> repositories = new HashMap<Repository, HashSet<IProject>>();

		for (IProject project : getProjectsInRepositoryOfSelectedResources()) {
			RepositoryMapping repositoryMapping = RepositoryMapping.getMapping(project);
			assert repositoryMapping != null;

			Repository repository = repositoryMapping.getRepository();

			HashSet<IProject> projects = repositories.get(repository);

			if (projects == null) {
				projects = new HashSet<IProject>();
				repositories.put(repository, projects);
			}

			projects.add(project);
		}

		for (Map.Entry<Repository, HashSet<IProject>> entry : repositories.entrySet()) {
			Repository repository = entry.getKey();
			HashSet<IProject> projects = entry.getValue();

			Tree head = repository.mapTree(Constants.HEAD);
			GitIndex index = repository.getIndex();
			IndexDiff indexDiff = new IndexDiff(head, index);
			indexDiff.diff();

			for (IProject project : projects) {
				includeList(project, indexDiff.getAdded(), indexChanges);
				includeList(project, indexDiff.getChanged(), indexChanges);
				includeList(project, indexDiff.getRemoved(), indexChanges);
				includeList(project, indexDiff.getMissing(), notIndexed);
				includeList(project, indexDiff.getModified(), notIndexed);
				addUntrackedFiles(repository, project);
			}
		}
	}

	private void addUntrackedFiles(final Repository repository, final IProject project) throws CoreException, IOException {
		final GitIndex index = repository.getIndex();
		final Tree headTree = repository.mapTree(Constants.HEAD);
		project.accept(new IResourceVisitor() {

			public boolean visit(IResource resource) throws CoreException {
				if (Team.isIgnoredHint(resource))
					return false;
				if (resource.getType() == IResource.FILE) {

					String repoRelativePath = RepositoryMapping.getMapping(project).getRepoRelativePath(resource);
					try {
						TreeEntry  headEntry = (headTree == null ? null : headTree.findBlobMember(repoRelativePath));
						if (headEntry == null){
							Entry indexEntry = null;
							indexEntry = index.getEntry(repoRelativePath);

							if (indexEntry == null) {
								notTracked.add((IFile)resource);
								files.add((IFile)resource);
							}
						}
					} catch (IOException e) {
						throw new TeamException(UIText.CommitAction_InternalError, e);
					}
				}
				return true;
			}
		});


