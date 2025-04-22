		if (input instanceof IAdaptable) {
			IResource resource = CommonUtils.getAdapter(((IAdaptable) input), IResource.class);
			if (resource != null) {
				RepositoryMapping mapping = RepositoryMapping
						.getMapping(resource);
				if (mapping != null)
					return mapping.getRepository();
