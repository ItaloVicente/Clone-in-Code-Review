		if (resource.getType() == IResource.PROJECT) {
			final RepositoryMapping mapping = RepositoryMapping
					.getMapping((IProject) resource);
			if (mapping == null || mapping.getRepository() != repository) {
				return false;
			}

			return true;
		}
