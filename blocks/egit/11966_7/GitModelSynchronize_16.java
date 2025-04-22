	public static final void synchronize(IResource[] resources,
			Repository repository, String srcRev, String dstRev,
			boolean includeLocal) throws IOException {
		final Set<IResource> includedResources = new HashSet<IResource>(
				Arrays.asList(resources));
		final Set<ResourceMapping> allMappings = new HashSet<ResourceMapping>();
