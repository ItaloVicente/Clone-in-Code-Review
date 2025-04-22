		IResource resource;
		if (selected instanceof IResource) {
			resource = (IResource) selected;
		} else if (selected instanceof IAdaptable) {
			resource = ((IAdaptable) selected)
					.getAdapter(IRESOURCE_TYPE);
		} else {
			resource = Platform.getAdapterManager().getAdapter(
					selected, IRESOURCE_TYPE);
		}
		return resource;
