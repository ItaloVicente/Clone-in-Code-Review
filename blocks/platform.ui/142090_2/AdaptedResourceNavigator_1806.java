		if (resource != null) {
			switch (resource.getType()) {
			case IResource.FILE:
				return resource.getParent();
			case IResource.FOLDER:
			case IResource.PROJECT:
			case IResource.ROOT:
				return (IContainer) resource;
			default:
				break;
			}
		}
		return ResourcesPlugin.getWorkspace().getRoot();
	}

	public AbstractUIPlugin getPlugin() {
