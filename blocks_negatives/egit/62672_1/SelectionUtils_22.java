			RepositoryMapping repositoryMapping = RepositoryMapping
					.getMapping(location);
			if (repositoryMapping == null)
				return null;
			if (mapping == null)
				mapping = repositoryMapping;
			if (mapping.getRepository() != repositoryMapping.getRepository()) {
				if (warn)
