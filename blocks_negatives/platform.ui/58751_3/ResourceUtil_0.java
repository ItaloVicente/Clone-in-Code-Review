		if (element == null) {
			return null;
		}
		if (element instanceof IResource) {
			return (IResource) element;
		}
		return getAdapter(element, IResource.class, true);
