		if (element instanceof IAdaptable)
			element = ((IAdaptable)element).getAdapter(IResource.class);
		if (element instanceof IResource) {
			IResource resource = (IResource) element;
			ResourceAttributes resourceAttributes = resource
					.getResourceAttributes();
			if (resourceAttributes != null
					&& resourceAttributes.isSymbolicLink())
