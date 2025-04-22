		ResourceState result = new ResourceState();
		switch (resource.getType()) {
		case IResource.FILE:
			extractResourceProperties(indexDiffData, resource, result);
			break;
		case IResource.PROJECT:
		case IResource.FOLDER:
			extractContainerProperties(indexDiffData, resource, result);
			break;
		default:
