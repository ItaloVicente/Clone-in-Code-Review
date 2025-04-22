		for (final Map.Entry<URIish, Map<String, Set<ProjectReference>>> entry : repositories.entrySet()) {
			final URIish gitUrl = entry.getKey();
			final Map<String, Set<ProjectReference>> branches = entry.getValue();

			for (final Map.Entry<String, Set<ProjectReference>> branchEntry : branches.entrySet()) {
				final String branch = branchEntry.getKey();
				final Set<ProjectReference> projects = branchEntry.getValue();

