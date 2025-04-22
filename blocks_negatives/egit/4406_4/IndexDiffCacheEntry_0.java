							final RepositoryMapping mapping = RepositoryMapping
									.getMapping(resource);
							if (mapping == null
									|| mapping.getRepository() != repository)
								return true;

