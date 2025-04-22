	public GitCompareEditorInput(String compareVersion, String baseVersion,
			Repository repository, IResource... resources) {
		super(new CompareConfiguration());
		this.repository = repository;
		this.resources = convertResourceInput(resources);
		this.baseVersion = baseVersion;
		this.compareVersion = compareVersion;
	}

	/**
	 * @param compareVersion
	 *            (shown on the left side in compare); currently only commit IDs
	 *            are supported
	 * @param baseVersion
	 *            (shown on the right side in compare); currently only commit
	 *            IDs are supported
	 * @param repository
	 *            as selected by the user
	 */
	public GitCompareEditorInput(String compareVersion, String baseVersion,
			Repository repository) {
		super(new CompareConfiguration());
		this.resources = new IResource[0];
		this.baseVersion = baseVersion;
		this.compareVersion = compareVersion;
		this.repository = repository;
