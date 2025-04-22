						IResource.DEPTH_ZERO, IResource.ALLOW_MISSING_LOCAL);
		} else if (objectType == Constants.OBJ_TREE) {
			IContainer folder = workspaceRoot.getContainerForLocation(path);
			if (folder != null)
				traversal = new ResourceTraversal(new IResource[] { folder },
						IResource.DEPTH_INFINITE, IResource.ALLOW_MISSING_LOCAL);
