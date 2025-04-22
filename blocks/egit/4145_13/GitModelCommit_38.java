	private final Commit commit;

	private final Repository repo;

	private final IProject[] projects;

	private final Map<String, GitModelObject> cachedTreeMap = new HashMap<String, GitModelObject>();
