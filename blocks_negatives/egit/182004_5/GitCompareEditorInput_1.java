	private final String compareVersion;

	private final IResource[] resources;

	private final List<String> filterPathStrings = new ArrayList<>();

	private final Map<IPath, IDiffContainer> diffRoots = new HashMap<>();

	private Repository repository;
