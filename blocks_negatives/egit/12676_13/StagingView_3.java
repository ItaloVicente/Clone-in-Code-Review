				IResource resource = AdapterUtils.adapt(element, IResource.class);
				if (resource != null) {
					RepositoryMapping mapping = RepositoryMapping.getMapping(resource);
					if (mapping != null && mapping.getRepository() == currentRepository) {
						String path = mapping.getRepoRelativePath(resource);
						else
							addPaths.add(path);
