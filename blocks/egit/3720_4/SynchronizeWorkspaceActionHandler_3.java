	private Map<Repository, Set<IContainer>> mapContainerResources(
			IResource[] resources) {
		Map<Repository, Set<IContainer>> result = new HashMap<Repository, Set<IContainer>>();

		for (IResource resource : resources) {
			RepositoryMapping rm = RepositoryMapping.getMapping(resource);
			if (resource instanceof IProject)
				result.put(rm.getRepository(), new HashSet<IContainer>());
			else if (resource instanceof IContainer) {
				Set<IContainer> containers = result.get(rm.getRepository());
				if (containers == null) {
					containers = new HashSet<IContainer>();
					result.put(rm.getRepository(), containers);
					containers.add((IContainer) resource);
				} else if (containers.size() > 0)
					containers.add((IContainer) resource);
			}
		}

		return result;
	}

