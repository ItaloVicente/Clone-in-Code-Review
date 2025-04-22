
		if (object instanceof IAdaptable) {
			IResource resource = (IResource) ((IAdaptable) object)
					.getAdapter(IResource.class);
			return resource == null ? false : typeOk(resource);
		}

		if (object instanceof IResource) {
			return typeOk((IResource) object);
