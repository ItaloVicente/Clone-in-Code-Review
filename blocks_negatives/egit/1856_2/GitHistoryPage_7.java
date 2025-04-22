		} else if (o instanceof HistoryPageInput)
			input = (HistoryPageInput) o;
		else if (o instanceof IAdaptable) {
			IResource resource = (IResource) ((IAdaptable) o)
					.getAdapter(IResource.class);
			if (resource != null) {
				RepositoryMapping mapping = RepositoryMapping
						.getMapping(resource);
				Repository repo = mapping.getRepository();
				input = new HistoryPageInput(repo, new IResource[] { resource });
