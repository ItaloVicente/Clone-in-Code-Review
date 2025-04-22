			if (resource != null) {
				RepositoryMapping mapping = RepositoryMapping
						.getMapping(resource);
				if (mapping != null)
					return mapping.getRepository();
			}
