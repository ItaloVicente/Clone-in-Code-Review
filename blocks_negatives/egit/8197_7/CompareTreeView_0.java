	private Map<IPath, GitFileRevision> compareVersionMap = new HashMap<IPath, GitFileRevision>();

	private Map<IPath, GitFileRevision> baseVersionMap = new HashMap<IPath, GitFileRevision>();

	private Set<IPath> addedPaths = new HashSet<IPath>();

	private Set<IPath> equalContentPaths = new HashSet<IPath>();

	private Set<IPath> baseVersionPathsWithChildren = new HashSet<IPath>();
