		if (input instanceof IResource) {
			RepositoryMapping mapping = RepositoryMapping
					.getMapping((IResource) input);
			if (mapping != null)
				return mapping.getRepository();
		}
		if (input instanceof IAdaptable) {
			IResource resource = (IResource) ((IAdaptable) input)
					.getAdapter(IResource.class);
			if (resource != null) {
				RepositoryMapping mapping = RepositoryMapping
						.getMapping(resource);
				if (mapping != null)
					return mapping.getRepository();
			}

		}
		throw new ExecutionException(
				UIText.AbstractHistoryCommanndHandler_CouldNotGetRepositoryMessage);
