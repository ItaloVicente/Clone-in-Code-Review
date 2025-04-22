				traversal = new ResourceTraversal(
						new IResource[] { container }, DEPTH_INFINITE,
						ALLOW_MISSING_LOCAL);
			} else {
				IFile file = root.getFileForLocation(location);
				if (file == null)
					continue;
