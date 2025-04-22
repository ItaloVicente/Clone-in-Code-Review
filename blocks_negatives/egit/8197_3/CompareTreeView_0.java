	private Map<IPath, GitFileRevision> baseVersionMap = new HashMap<IPath, GitFileRevision>();

	private Set<IPath> addedPaths = new HashSet<IPath>();

	private Set<IPath> equalContentPaths = new HashSet<IPath>();

	private Set<IPath> baseVersionPathsWithChildren = new HashSet<IPath>();

	private Map<IPath, List<PathNodeAdapter>> compareVersionPathsWithChildren = new HashMap<IPath, List<PathNodeAdapter>>();

	private Set<IPath> deletedPaths = new HashSet<IPath>();
