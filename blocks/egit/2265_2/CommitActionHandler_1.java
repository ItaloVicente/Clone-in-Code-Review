	static class CountingVisitor implements IResourceVisitor {
		int count;
		public boolean visit(IResource resource) throws CoreException {
			count++;
			return true;
		}
	}
