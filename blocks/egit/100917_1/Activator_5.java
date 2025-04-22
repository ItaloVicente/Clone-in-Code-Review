		private Set<IPath> getProjectLocations(Repository repository) {
			IProject[] projects = RuleUtil.getProjects(repository);
			if (projects == null) {
				return Collections.emptySet();
			}
			Set<IPath> result = new HashSet<>();
			for (IProject project : projects) {
				if (project.isAccessible()) {
					IPath path = project.getLocation();
					if (path != null) {
						result.add(path);
