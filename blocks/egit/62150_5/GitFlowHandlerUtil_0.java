		if (repository == null) {
			IResource resource = Utils.getAdapter((IAdaptable) firstElement,
					IResource.class);
			if (resource != null) {
				repository = Utils.getAdapter(resource, Repository.class);
			}
		}
