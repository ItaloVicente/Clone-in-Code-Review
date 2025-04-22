	private ResourceTraversal createTraversalForFile(GitModelObject aChild) {
		IPath childLocation = aChild.getLocation();
		IFile file = ROOT.getFileForLocation(childLocation);

		if(file != null) {
			ResourceTraversal traversal = new ResourceTraversal(new IResource[] { file },
					DEPTH_ONE, ALLOW_MISSING_LOCAL);
			return traversal;
		}
		return null;
