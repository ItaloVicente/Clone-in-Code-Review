		final IAdaptable element = getElement();
		if (element instanceof IResource) {
			project = ((IResource) element).getProject();
		} else {
			IResource adapter = AdapterUtils.adapt(element, IResource.class);
			if (adapter != null) {
				project = adapter.getProject();
			}
