	private IResourceVariant[] fetchMembersImpl(IResourceVariant variant,
			IProgressMonitor monitor) throws TeamException {
		IContainer container = getContainer(variant);
		Set<IResourceVariant> result = new HashSet<IResourceVariant>();
		GitSynchronizeData gsd = gsds.getData(container.getProject());
		Tree treeEntry = getTree(container, gsd);
