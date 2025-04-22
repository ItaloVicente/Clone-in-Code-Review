        IResource resource = null;
        if (element instanceof IResource) {
			resource = (IResource) element;
		}
        if (element instanceof IAdaptable) {
			resource = ((IAdaptable) element).getAdapter(IResource.class);
		}
