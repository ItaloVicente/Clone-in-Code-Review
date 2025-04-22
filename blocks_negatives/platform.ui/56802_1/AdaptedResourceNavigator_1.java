        IResource resource = null;
        if (input instanceof IResource) {
            resource = (IResource) input;
        } else {
			resource = input.getAdapter(IResource.class);
        }
