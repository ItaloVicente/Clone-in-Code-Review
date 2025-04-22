				selection, IResource.class)) {
			RepositoryMapping mapping = RepositoryMapping.getMapping(resource);
			if (mapping != null && (mapping.getContainer() instanceof IProject))
				ret.add((IProject) mapping.getContainer());
			else
				return new IProject[0];
		}
