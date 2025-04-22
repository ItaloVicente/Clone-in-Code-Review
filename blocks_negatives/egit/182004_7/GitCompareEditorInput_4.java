	public GitCompareEditorInput(String compareVersion, String baseVersion,
			Repository repository) {
		super(new CompareConfiguration());
		this.resources = new IResource[0];
		this.baseVersion = baseVersion;
		this.compareVersion = compareVersion;
		this.repository = repository;
