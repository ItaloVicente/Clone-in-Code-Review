	public static IProject[] getSelectedProjects(
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

	private static Set<IProject> extractProjectsFromMappings(
			IStructuredSelection selection) {
		Set<IProject> ret = new LinkedHashSet<>();
		for (ResourceMapping mapping : getSelectedAdaptables(selection,
				ResourceMapping.class)) {
			IProject[] mappedProjects = mapping.getProjects();
			if (mappedProjects != null && mappedProjects.length != 0) {
				List<IProject> projects = new ArrayList<>(
						Arrays.asList(mappedProjects));
				Collections.sort(projects,
						CommonUtils.RESOURCE_NAME_COMPARATOR);
				ret.addAll(projects);
			}
		}
		return ret;
	}

	@NonNull
	private static <T> List<T> getSelectedAdaptables(ISelection selection,
			Class<T> c) {
		List<T> result;
		if (selection != null && !selection.isEmpty()) {
			result = new ArrayList<>();
			Iterator elements = ((IStructuredSelection) selection).iterator();
			while (elements.hasNext()) {
				T adapter = AdapterUtils.adapt(elements.next(), c);
				if (adapter != null) {
					result.add(adapter);
				}
			}
		} else {
			result = Collections.emptyList();
		}
		return result;
	}

	@NonNull
	private static Repository[] getRepositoriesFor(final IProject[] projects) {
		Set<Repository> ret = new LinkedHashSet<>();
		for (IProject project : projects) {
			RepositoryMapping repositoryMapping = RepositoryMapping
					.getMapping(project);
			if (repositoryMapping == null) {
				return new Repository[0];
			}
			ret.add(repositoryMapping.getRepository());
		}
		return ret.toArray(new Repository[0]);
	}
