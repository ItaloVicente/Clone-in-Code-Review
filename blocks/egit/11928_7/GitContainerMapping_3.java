
		ResourceTraversal traversal = null;
		if (dataSet == null)
			traversal = new ResourceTraversal(new IResource[] { file },
					DEPTH_ONE, ALLOW_MISSING_LOCAL);
		else if (file != null && dataSet.shouldBeIncluded(file))
			traversal = new ResourceTraversal(new IResource[] { file },
					DEPTH_ONE, ALLOW_MISSING_LOCAL);

