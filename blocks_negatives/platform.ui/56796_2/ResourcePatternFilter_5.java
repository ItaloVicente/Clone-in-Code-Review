        IResource resource = null;
        if (element instanceof IResource) {
            resource = (IResource) element;
        } else if (element instanceof IAdaptable) {
            IAdaptable adaptable = (IAdaptable) element;
            resource = adaptable.getAdapter(IResource.class);
        }
