			if (repositoryMapping == null)
				continue;
			Repository repository = repositoryMapping.getRepository();
			Collection<String> resourcesList = result.get(repository);
			if (resourcesList == null) {
				resourcesList = new ArrayList<String>();
				result.put(repository, resourcesList);
