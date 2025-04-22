			RepositoryMapping mapping = RepositoryMapping.getMapping(project);
			if (mapping != null) {
				Repository repository = mapping.getRepository();
				Set<IProject> set = repositories.get(repository);
				if (set == null) {
					set = new HashSet<IProject>();
					repositories.put(repository, set);
				}
				set.add(project);
