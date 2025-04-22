	/**
	 * Retrieve the list of projects that contains the given resources. All
	 * resources must actually map to a project shared with egit, otherwise an
	 * empty array is returned. In case of a linked resource, the project
	 * returned is the one that contains the link target and is shared with
	 * egit, if any, otherwise an empty array is also returned.
	 *
	 * @param selection
	 * @return the projects hosting the selected resources
	 */
	private IProject[] getProjectsForSelectedResources(
			IStructuredSelection selection) {
		Set<IProject> ret = new LinkedHashSet<>();
		for (IResource resource : getSelectedAdaptables(selection,
				IResource.class)) {
			RepositoryMapping mapping = RepositoryMapping.getMapping(resource);
			if (mapping != null && (mapping.getContainer() instanceof IProject))
				ret.add((IProject) mapping.getContainer());
			else
				return new IProject[0];
		}
		ret.addAll(extractProjectsFromMappings(selection));

		return ret.toArray(new IProject[0]);
	}

	private Set<IProject> extractProjectsFromMappings(
			IStructuredSelection selection) {
		Set<IProject> ret = new LinkedHashSet<>();
		for (ResourceMapping mapping : getSelectedAdaptables(selection,
				ResourceMapping.class)) {
			IProject[] mappedProjects = mapping.getProjects();
			if (mappedProjects != null && mappedProjects.length != 0) {
				List<IProject> projects = new ArrayList<>(
						Arrays.asList(mappedProjects));
				Collections
						.sort(projects, CommonUtils.RESOURCE_NAME_COMPARATOR);
				ret.addAll(projects);
			}
		}
		return ret;
	}
