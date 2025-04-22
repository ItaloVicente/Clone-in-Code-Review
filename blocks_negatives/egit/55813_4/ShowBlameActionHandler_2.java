			if (resource instanceof IStorage) {
				IStorage storage = (IStorage) resource;
				RepositoryMapping mapping = RepositoryMapping
						.getMapping(resource);

				if (mapping != null) {
					String repoRelativePath = mapping
							.getRepoRelativePath(resource);
					return new Data(mapping.getRepository(), repoRelativePath,
							storage, null);
				}
