	/**
	 * @param repo
	 * @return set of project connected with this particular repository
	 */
	protected Set<IProject> getProcjetsInRepository(Repository repo) {
		Set<IProject> ret = new HashSet<IProject>();
		final IProject[] projects = ResourcesPlugin.getWorkspace().getRoot().getProjects();
		for (IProject project : projects) {
			RepositoryMapping mapping = RepositoryMapping.getMapping(project);
			if (mapping != null && mapping.getRepository() == repo) {
				ret.add(project);
			}
		}
		return ret;
	}

