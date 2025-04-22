			RepositoryMapping mapping = RepositoryMapping.getMapping(res
					.getProject());
			return mapping != null && mapping.getRepository() != null;
			RepositoryMapping mapping = RepositoryMapping.getMapping(res
					.getProject());
			return mapping != null
					&& SAFE == mapping.getRepository().getRepositoryState();
