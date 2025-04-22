    protected IAdaptable getInitialInput() {
        IAdaptable input = getSite().getPage().getInput();
        if (input != null) {
            IResource resource = null;
            if (input instanceof IResource) {
                resource = (IResource) input;
            } else {
                resource = input.getAdapter(IResource.class);
            }
            if (resource != null) {
                switch (resource.getType()) {
                case IResource.FILE:
                    return resource.getParent();
                case IResource.FOLDER:
                case IResource.PROJECT:
                case IResource.ROOT:
                    return resource;
                default:
                    break;
                }
            }
        }
        return ResourcesPlugin.getWorkspace().getRoot();
    }
