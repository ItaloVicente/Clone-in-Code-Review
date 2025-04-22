				traversal = new ResourceTraversal(
						new IResource[] { container }, DEPTH_INFINITE,
						ALLOW_MISSING_LOCAL);
			} else {
				IFile file = ROOT.getFileForLocation(location);
				if (file == null)
					continue;
