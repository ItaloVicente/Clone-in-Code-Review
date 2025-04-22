	private void addIgnore(IProgressMonitor monitor, IPath path)
			throws UnsupportedEncodingException, CoreException, IOException {
		IPath parent = path.removeLastSegments(1);
		IResource resource = ResourceUtil.getResourceForLocation(path, false);
		IContainer container = null;
		boolean isDirectory = false;
		if (resource != null) {
			isDirectory = resource instanceof IContainer;
			container = resource.getParent();
		} else
			isDirectory = path.toFile().isDirectory();

		b.append(path.lastSegment());
		if (isDirectory)
			b.append('/');
		b.append('\n');
		String entry = b.toString();

		if (container == null || container instanceof IWorkspaceRoot) {
