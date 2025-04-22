	private Set<IProject> extractProjectsFromMappings(IStructuredSelection selection) {
		Set<IProject> ret = new HashSet<IProject>();
		for (ResourceMapping mapping : (ResourceMapping[]) getSelectedAdaptables(
				selection, ResourceMapping.class)) {
			IProject[] projects = mapping.getProjects();
			ret.addAll(Arrays.asList(projects));
		}
		return ret;
	}

