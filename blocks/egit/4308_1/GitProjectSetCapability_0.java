
		try {
			ResourcesPlugin.getWorkspace().run(new IWorkspaceRunnable() {

				public void run(IProgressMonitor monitor) throws CoreException {
					final Map<URIish, Map<String, Set<ProjectReference>>> repositories = new LinkedHashMap<URIish, Map<String, Set<ProjectReference>>>();
					for (final String reference : referenceStrings) {
						try {
							final ProjectReference projectReference = new ProjectReference(reference);
							Map<String, Set<ProjectReference>> repositoryBranches = repositories.get(projectReference.repository);
							if (repositoryBranches == null) {
								repositoryBranches = new HashMap<String, Set<ProjectReference>>();
								repositories.put(projectReference.repository, repositoryBranches);
							}
							Set<ProjectReference> projectReferences = repositoryBranches.get(projectReference.branch);
							if (projectReferences == null) {
								projectReferences = new TreeSet<ProjectReference>(new ProjectReferenceComparator());
								repositoryBranches.put(projectReference.branch, projectReferences);
							}

							projectReferences.add(projectReference);
						} catch (final IllegalArgumentException e) {
							throw new TeamException(reference, e);
						} catch (final URISyntaxException e) {
							throw new TeamException(reference, e);
						}
