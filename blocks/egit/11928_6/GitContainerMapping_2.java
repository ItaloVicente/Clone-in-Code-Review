
		ResourceTraversal traversal = null;
		if (dataSet == null)
			traversal = new ResourceTraversal(new IResource[] { file },
					DEPTH_ONE, ALLOW_MISSING_LOCAL);
		else if (file != null && shouldBeIncluded(file, dataSet))
			traversal = new ResourceTraversal(new IResource[] { file },
					DEPTH_ONE, ALLOW_MISSING_LOCAL);

