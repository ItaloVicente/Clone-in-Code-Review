
		if (element instanceof IAdaptable) {
			IResource resource = CommonUtils.getAdapter(((IAdaptable) element), IResource.class);

			if (resource != null && resource.exists())
				return resource;
