
		ResourceTraversal traversal = null;
		if (file != null && shouldBeIncluded(file, dataSet))
			traversal = new ResourceTraversal(new IResource[] { file },
					DEPTH_ONE, ALLOW_MISSING_LOCAL);

