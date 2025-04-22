		final Map<URIish, Map<String, Set<ProjectReference>>> repositories =
				new LinkedHashMap<URIish, Map<String, Set<ProjectReference>>>();
		for (final String reference : referenceStrings) {
			try {
				final ProjectReference projectReference = new ProjectReference(
						reference);
				Map<String, Set<ProjectReference>> repositoryBranches = repositories
						.get(projectReference.repository);
				if (repositoryBranches == null) {
					repositoryBranches = new HashMap<String, Set<ProjectReference>>();
					repositories.put(projectReference.repository,
							repositoryBranches);
				}
				Set<ProjectReference> projectReferences = repositoryBranches.get(projectReference.branch);
				if (projectReferences == null) {
					projectReferences = new TreeSet<ProjectReference>(new ProjectReferenceComparator());
					repositoryBranches.put(projectReference.branch, projectReferences);
				}
