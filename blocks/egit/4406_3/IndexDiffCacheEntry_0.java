							final RepositoryMapping mapping = RepositoryMapping
									.getMapping(resource);
							if (mapping == null
									|| mapping.getRepository() != repository)
								return true;

							if (resource instanceof IFolder && delta.getKind() == IResourceDelta.ADDED) {
								filesToUpdate.add(mapping.getRepoRelativePath(resource));
								resourcesToUpdate.add(resource);
								return true;
							}
