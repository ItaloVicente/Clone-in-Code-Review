	protected IAdaptable getInitialInput() {
		IResource resource = Adapters.getAdapter(getSite().getPage().getInput(), IResource.class, true);
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
		return ResourcesPlugin.getWorkspace().getRoot();
	}
