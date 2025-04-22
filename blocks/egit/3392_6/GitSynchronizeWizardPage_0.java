			RepositoryMapping repositoryMapping = RepositoryMapping
					.getMapping(project);
			if (repositoryMapping == null)
				continue;
			Repository repo = repositoryMapping.getRepository();
			Set<IProject> projects = resources.get(repo);
			if (projects == null) {
				projects = new HashSet<IProject>();
				resources.put(repo, projects);
